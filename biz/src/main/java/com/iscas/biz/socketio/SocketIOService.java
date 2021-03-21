package com.iscas.biz.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.iscas.common.tools.core.random.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/18 11:11
 * @since jdk1.8
 */
@Service(value = "socketIOService")
public class SocketIOService {

    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception  {
        stop();
    }

    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                clientMap.put(loginUserNum, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                clientMap.remove(loginUserNum);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener("messageevent", String.class, (client, data, ackSender) -> {
            ackSender.sendAckData(1);
            while(true) {
                client.sendEvent("messageevent", RandomStringUtils.randomStr(20) + Math.random());
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        socketIOServer.start();
    }

    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

//    @Override
//    public void pushMessageToUser(PushMessage pushMessage) {
//        String loginUserNum = pushMessage.getLoginUserNum();
//        if (StringUtils.isNotBlank(loginUserNum)) {
//            SocketIOClient client = clientMap.get(loginUserNum);
//            if (client != null)
//                client.sendEvent(PUSH_EVENT, pushMessage);
//        }
//    }

    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        String name = client.getHandshakeData().getSingleUrlParam("name");
        return name;
    }
}
