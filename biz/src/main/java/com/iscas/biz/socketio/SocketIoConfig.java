package com.iscas.biz.socketio;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.iscas.common.tools.core.random.RandomStringUtils;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/17 20:41
 * @since jdk1.8
 */
@org.springframework.context.annotation.Configuration
public class SocketIoConfig {
    public Map<String, SocketIOClient> socketIoMap = new HashMap<>();


    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname("192.168.228.84");
        config.setPort(8086);
        config.setBossThreads(1);
        config.setWorkerThreads(10);
        config.setAllowCustomRequests(true);
        config.setUpgradeTimeout(10000);
        config.setPingTimeout(50000);
        config.setPingInterval(20000);
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
