package com.iscas.biz.service.common;

import com.iscas.biz.mapper.common.WsDataMapper;
import com.iscas.biz.model.common.WsData;
import com.iscas.templet.exception.BaseException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
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

    private final WsDataMapper wsDataMapper;

    public WsService(SimpMessagingTemplate messagingTemplate, WsDataMapper wsDataMapper) {
        this.messagingTemplate = messagingTemplate;
        this.wsDataMapper = wsDataMapper;
    }

    /**
     * 广播消息
     * */
    public void broadCast(String destination, WsData data) throws BaseException {
        boolean persistent = data.isPersistent();
        if (persistent) {
            throw new BaseException("广播消息暂不支持持久化");
        }
        messagingTemplate.convertAndSend(destination, data);
    }

    /**
     * 点对点消息
     * */
    public void p2p(WsData wsData) {
        messagingTemplate.convertAndSendToUser(wsData.getUserIdentity(), wsData.getDestination(), wsData);
        //如果需要持久化，存储
        if (wsData.isPersistent()) {
            storeToDb(wsData);
        }
    }

    @Async("wsExecutor")
    public void storeToDb(WsData wsData) {
        com.iscas.biz.domain.common.WsData dbWsData = com.iscas.biz.domain.common.WsData.convert(wsData);
        wsDataMapper.insert(dbWsData);
    }

}
