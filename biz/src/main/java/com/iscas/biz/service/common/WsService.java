package com.iscas.biz.service.common;

import com.iscas.biz.model.common.WsData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * websocket消息
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/26 9:10
 * @since jdk1.8
 */
@Service
public class WsService {
    private final SimpMessagingTemplate messagingTemplate;

    public WsService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 广播消息
     * */
    public void broadCast(String destination, Object data) {
        messagingTemplate.convertAndSend(destination, data);
    }

    /**
     * 点对点消息
     * */
    public void p2p(WsData wsData) {
        messagingTemplate.convertAndSendToUser(wsData.getUserIdentity(), wsData.getDestination(), wsData);
        //如果需要持久化，存储
        //todo
    }

}
