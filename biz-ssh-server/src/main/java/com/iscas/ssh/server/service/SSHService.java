package com.iscas.ssh.server.service;

import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import com.iscas.ssh.server.constant.CommonConstants;
import com.iscas.ssh.server.model.SSHConnection;
import com.iscas.ssh.server.model.SftpFile;
import com.iscas.ssh.server.model.UploadProgress;
import com.iscas.ssh.server.model.WebSSHData;
import com.iscas.templet.exception.BaseException;
import com.jcraft.jsch.*;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.*;

/**
 * 处理SSH连接的业务
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/27 14:10
 * @since jdk1.8
 */
@Service
@Slf4j
public class SSHService {
    //存放ssh连接信息的map
    private static Map<String, SSHConnection> sshMap = new ConcurrentHashMap<>();

    //连接ID对应的用户
    private static Map<String, String> connectionUserMap = new ConcurrentHashMap<>();

    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

    private int connectionTimeout = 30;

    private int channelTimeout = 3;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpUserRegistry userRegistry;

    /**订阅上传进度条地址*/
    private static final String UPLOAD_PROGRESS_DEST = "/queue/upload/progress";

    /**
     * 发送心跳
     * */
    public void sendHeartbeat() {
        ses.scheduleAtFixedRate(() -> {
            if (MapUtils.isNotEmpty(sshMap)) {
                for (Map.Entry<String, SSHConnection> entry : sshMap.entrySet()) {
                    SSHConnection sshConnection = entry.getValue();
                    String connectionId = sshConnection.getConnectionId();
                    String username = connectionUserMap.get(connectionId);
                    //如果用户连接丢失，直接断掉
                    if (userRegistry.getUser(username) == null) {
                        close(connectionId);
                    } else {
                        //发送心跳
                        messagingTemplate.convertAndSendToUser(username, "/queue/ping/".concat(connectionId), "ping");
                    }
                }
            }
        }, 2, 15, TimeUnit.SECONDS);
    }

    /**
     * 处理丢掉的连接
     * */
    public void clearLostConnection() {
        ses.scheduleAtFixedRate(() -> {
            if (MapUtils.isNotEmpty(sshMap)) {
                for (Map.Entry<String, SSHConnection> entry : sshMap.entrySet()) {
                    SSHConnection sshConnection = entry.getValue();
                    String connectionId = sshConnection.getConnectionId();
                    String username = connectionUserMap.get(connectionId);
                    //如果用户连接丢失，直接断掉
                    if (userRegistry.getUser(username) == null) {
                        close(connectionId);
                    } else {
                        //处理30S还未收到心跳返回的连接
                        long lastHeartbeatTime = sshConnection.getLastHeartbeatTime();
                        if (new Date().getTime() - lastHeartbeatTime > 30000) {
                            close(connectionId);
                        }
                    }
                }
            }
        }, 15, 15, TimeUnit.SECONDS);
    }


    /**
     * 初始化连接
     */
    public void initConnection(String connectionId, Principal user) {
        JSch jSch = new JSch();
        SSHConnection sshConnection = new SSHConnection();
        sshConnection.setJSch(jSch);
        sshConnection.setConnectionId(connectionId);
        sshConnection.setLastHeartbeatTime(System.currentTimeMillis());
//        将这个ssh连接信息放入map中
        sshMap.put(connectionId, sshConnection);
        connectionUserMap.put(connectionId, user.getName());
    }

    /**
     * @Description: 处理客户端发送的数据
     */
    public void recvHandle(WebSSHData webSSHData) throws IOException, JSchException {
        String connectionId = webSSHData.getConnectionId();
        if (CommonConstants.WEBSSH_OPERATE_CONNECT.equals(webSSHData.getOperate())) {
            //找到刚才存储的ssh连接对象
            SSHConnection sshConnection = (SSHConnection) sshMap.get(connectionId);
            //启动线程异步处理
            WebSSHData finalWebSSHData = webSSHData;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connectToSSH(sshConnection, finalWebSSHData);
                    } catch (JSchException | IOException e) {
                        log.error("webssh连接异常", e.getMessage());
                        close(connectionId);
                    }
                }
            });
        } else if (CommonConstants.WEBSSH_OPERATE_COMMAND.equals(webSSHData.getOperate())) {
            String command = webSSHData.getCommand();
            SSHConnection sshConnection = (SSHConnection) sshMap.get(connectionId);
            if (sshConnection != null) {
                try {
                    transToSSH(sshConnection.getChannel(), command);
                } catch (IOException e) {
                    log.error("webssh连接异常", e.getMessage());
                    close(connectionId);
                }
            } else {
                sendMessage(connectionId, "连接已断开");
            }
        } else {
            log.error("不支持的操作");
            close(connectionId);
        }
    }

    public void sendMessage(String connectionId, byte[] buffer) throws IOException {
        String username = connectionUserMap.get(connectionId);
        if (username == null) {
            throw new RuntimeException(String.format("未找到connectionId:[%s]对应的websocket连接用户", connectionId));
        }
        messagingTemplate.convertAndSendToUser(username, "/queue/".concat(connectionId), new String(buffer, "utf-8"));
    }

    public void sendMessage(String connectionId, String data) throws IOException {
        String username = connectionUserMap.get(connectionId);
        if (username == null) {
            throw new RuntimeException(String.format("未找到connectionId:[%s]对应的websocket连接用户", connectionId));
        }
        messagingTemplate.convertAndSendToUser(username, "/queue/".concat(connectionId), data);
    }

    public void close(String connectionId) {
        SSHConnection sshConnection = (SSHConnection) sshMap.get(connectionId);
        if (sshConnection != null) {
            //断开连接
            if (sshConnection.getChannel() != null) sshConnection.getChannel().disconnect();
            //map中移除
            sshMap.remove(connectionId);
        }
    }

    /**
     * 使用jsch连接终端
     */
    private void connectToSSH(SSHConnection sshConnection, WebSSHData webSSHData) throws JSchException, IOException {

        Session session = null;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        session = sshConnection.getJSch().getSession(webSSHData.getUsername(), webSSHData.getHost(), webSSHData.getPort());
        session.setConfig(config);
        sshConnection.setSession(session);

        //设置密码
        session.setPassword(webSSHData.getPassword());
        //连接  超时时间30s
        session.connect(connectionTimeout * 1000);

        //开启shell通道
        Channel channel = session.openChannel("shell");
        //通道连接 超时时间3s
        channel.connect(channelTimeout * 1000);

        //设置channel
        sshConnection.setChannel(channel);

        //转发消息
        transToSSH(channel, "\r\n");

//        //读取终端返回的信息流
        InputStream inputStream = channel.getInputStream();
//        try {
//            InputStreamReader isr = new InputStreamReader(inputStream);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                sendMessage(sshConnection.getConnectionId(), line);
//            }
//        } finally {
//            //断开连接后关闭会话
//            session.disconnect();
//            channel.disconnect();
//            if (inputStream != null) {
//                inputStream.close();
//            }
//        }

        try {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
//            byte[] toSendBytes = null;
//            List<Byte> bytes = new ArrayList<>();
//            List<Byte> lastBytes = new ArrayList<>();
//            while ((i = inputStream.read(buffer)) != -1) {
//                for (int j = 0; j < i; j++) {
//
//                    byte b = buffer[j];
//                    System.out.print((char)b);
//                    lastBytes.add(b);
//                    if (b == '\n' || b == '\r' || b == '>') {
//                        bytes.addAll(lastBytes);
//                        lastBytes.clear();
//                    }
//                }
//                if (bytes.size() > 0) {
//                    toSendBytes = new byte[bytes.size()];
//                    for (int i1 = 0; i1 < bytes.size(); i1++) {
//                        toSendBytes[i1] = bytes.get(i1);
//                    }
//                    sendMessage(webSSHData.getConnectionId(), toSendBytes);
//                }
//            }
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessage(webSSHData.getConnectionId(), Arrays.copyOfRange(buffer, 0, i));
            }

        } finally {
            //断开连接后关闭会话
            session.disconnect();
            channel.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    /**
     * 将消息转发到终端
     */
    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }

    private void transToSSH(PrintWriter pw, String command) throws IOException {
        if (pw != null) {
            pw.println(command);
            pw.flush();
        }
    }

    /**
     * 心跳的pong信息
     * */
    public void pong(String connectionId) {
        SSHConnection sshConnection = sshMap.get(connectionId);
        if (sshConnection != null) {
            sshConnection.setLastHeartbeatTime(new Date().getTime());
        }
    }


    public List<SftpFile> listDir(String connectionId, String dir) throws JSchException, BaseException, SftpException {
        SSHConnection sshConnection = sshMap.get(connectionId);
        List<SftpFile> sftpFiles = new ArrayList<>();
        if (sshConnection != null) {
            Session session = sshConnection.getSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            try {
                sftp.connect(channelTimeout * 1000);
                if (isFile(sftp, dir)) {
                    throw new BaseException(String.format("%s是一个文件，不是目录", dir));
                }
                Vector ls = sftp.ls(dir);
                Iterator iterator = ls.iterator();
                while (iterator.hasNext()) {
                    ChannelSftp.LsEntry next = (ChannelSftp.LsEntry) iterator.next();
                    String filename = next.getFilename();
                    if (StringUtils.equals(filename, ".") || StringUtils.equals(filename, "..")) {
                        continue;
                    }
                    String path = Objects.equals(dir, "/") ? dir + filename : dir + "/" + filename;
                    SftpFile sftpFile = new SftpFile();
                    sftpFile.setName(filename);
                    sftpFile.setPath(path);
                    if (isFile(sftp, path)) {
                        sftpFile.setFile(true);
                        SftpATTRS attrs = next.getAttrs();
                        sftpFile.setSize(attrs.getSize() / 1024);
                    } else {
                        sftpFile.setFile(false);
                    }
                    sftpFiles.add(sftpFile);
                }
            } finally {
                if (sftp != null) {
                    sftp.disconnect();
                }
            }
        }
        Collections.sort(sftpFiles, new Comparator<SftpFile>() {
            @Override
            public int compare(SftpFile o1, SftpFile o2) {
                boolean file1 = o1.isFile();
                boolean file2 = o2.isFile();
                if (file1 && !file2) {
                    return 1;
                }
                return -1;
            }
        });
        return sftpFiles;

    }



    /**
     * 下载文件
     * */
    public void download(String connectionId, String path) throws Exception {
        SSHConnection sshConnection = sshMap.get(connectionId);
        if (sshConnection != null) {
            Session session = sshConnection.getSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            try {
                sftp.connect(channelTimeout * 1000);
                if (!isFile(sftp, path)) {
                   throw new BaseException(String.format("%s是一个目录，不支持目录下载", path));
                }
                String filename = StringUtils.substringAfterLast(path, "/");
                @Cleanup InputStream is = sftp.get(path);
                HttpServletRequest request = SpringUtils.getRequest();
                HttpServletResponse response = SpringUtils.getResponse();
//                FileDownloadUtils.setResponseHeader(request, response, filename);
                FileDownloadUtils.downByStream(request, response, is, filename);
            } finally {
                if (sftp != null) {
                    sftp.disconnect();
                }
            }
        } else {
            throw new BaseException("SSH连接不存在");
        }
    }

    /**
     * 文件上传
     * */
    public void upload(String connectionId, MultipartFile[] files, String dest) throws BaseException, JSchException, IOException, SftpException {
        SSHConnection sshConnection = sshMap.get(connectionId);
        if (sshConnection != null) {
            String user = connectionUserMap.get(sshConnection.getConnectionId());
            if (user == null) {
                throw new BaseException("websocket连接不存在，无法上传");
            }
            Session session = sshConnection.getSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            try {
                sftp.connect(channelTimeout * 1000);
                int total = files.length;
                int[] current = new int[1];
                String id = UUID.randomUUID().toString();
                copy(sftp, files, dest, current, total, id, user);
            } finally {
                if (sftp != null) {
                    sftp.disconnect();
                }
            }
        } else {
            throw new BaseException("ssh连接不存在");
        }
    }

    private void copy(ChannelSftp sftp, MultipartFile[] files, String dest, int[] current, int total, String id, String user)
            throws BaseException, IOException, SftpException {
        try {
            sftp.cd(dest);
        } catch (SftpException e) {
            throw new BaseException(String.format("进入目录服务目录：%s错误", dest), e);
        }
        for (MultipartFile file : files) {
            @Cleanup InputStream is = file.getInputStream();
            @Cleanup BufferedInputStream bis = new BufferedInputStream(is);
            sftp.put(bis, file.getOriginalFilename());
            messagingTemplate.convertAndSendToUser(user, UPLOAD_PROGRESS_DEST,
                    new UploadProgress(id, total, ++current[0]));
        }

    }

    /**
     * 判断远程服务器的路径是否为文件
     * */
    private boolean isFile(ChannelSftp sftp, String dir) throws SftpException {
        boolean isFile = true;
        Vector ls = sftp.ls(dir);
        Iterator iterator = ls.iterator();
        while (iterator.hasNext()) {
            ChannelSftp.LsEntry next = (ChannelSftp.LsEntry) iterator.next();
            String filename = next.getFilename();
            if (Objects.equals(".", filename)) {
                isFile = false;
                break;
            }
        }
        return isFile;
    }

}