package com.iscas.ssh.server.service;

import com.iscas.common.ssh.tools.SshClientUtils;
import com.iscas.common.ssh.tools.model.SshClientDto;
import com.iscas.ssh.server.constant.CommonConstants;
import com.iscas.ssh.server.model.SSHConnection;
import com.iscas.ssh.server.model.WebSSHData;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private static Map<String, Object> sshMap = new ConcurrentHashMap<>();

    //连接ID对应的用户
    private static Map<String, String> connectionUserMap = new ConcurrentHashMap<>();

    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private int connectionTimeout = 30;

    private int channelTimeout = 3;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 初始化连接
     */
    public void initConnection(String connectionId, Principal user) {
        JSch jSch = new JSch();
        SSHConnection sshConnection = new SSHConnection();
        sshConnection.setJSch(jSch);
        sshConnection.setConnectionId(connectionId);
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
}
