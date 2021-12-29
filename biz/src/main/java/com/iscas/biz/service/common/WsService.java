package com.iscas.biz.service.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.biz.mapper.common.WsDataMapper;
import com.iscas.biz.model.common.WsData;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.templet.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * websocket消息
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/26 9:10
 * @since jdk1.8
 */
@Service
@Slf4j
@ConditionalOnMybatis
public class WsService {
    private WsDataMapper getWsDataMapper() {
        try {
            return SpringUtils.getBean(WsDataMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    private final SimpMessagingTemplate messagingTemplate;

//    private final WsDataMapper wsDataMapper;

    public WsService(SimpMessagingTemplate messagingTemplate/*, WsDataMapper wsDataMapper*/) {
        this.messagingTemplate = messagingTemplate;
//        this.wsDataMapper = wsDataMapper;
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
        //如果需要持久化，存储
        if (wsData.isPersistent() && getWsDataMapper() != null) {
            storeToDb(wsData);
        }
        messagingTemplate.convertAndSendToUser(wsData.getUserIdentity(), wsData.getDestination(), wsData);
    }

//    @Async("asyncExecutor")
    public void storeToDb(WsData wsData) {
        com.iscas.biz.domain.common.WsData dbWsData = com.iscas.biz.domain.common.WsData.convert(wsData);
        getWsDataMapper().insert(dbWsData);
    }

    public void retry(Integer id) {
        com.iscas.biz.domain.common.WsData wsData = getWsDataMapper().selectById(id);
        WsData wsData1 = com.iscas.biz.domain.common.WsData.convert(wsData);
        p2p(wsData1);
    }

    //todo 需要做用户权限判定
    @Async("asyncExecutor")
    public void ack(String msgId) {
        if (getWsDataMapper() == null) {
            return;
        }
//        WsDataExample dataExample = new WsDataExample();
//        dataExample.createCriteria().andMsgIdEqualTo(msgId);
//        List<com.iscas.biz.domain.common.WsData> wsDatas = getWsDataMapper().selectByExample(dataExample);
        List<com.iscas.biz.domain.common.WsData> wsDatas = getWsDataMapper().selectList(new QueryWrapper<com.iscas.biz.domain.common.WsData>()
                .lambda().eq(com.iscas.biz.domain.common.WsData::getMsgId, msgId));
        if (wsDatas != null) {
            for (com.iscas.biz.domain.common.WsData wsData : wsDatas) {
                wsData.setAck(true);
                try {
                    getWsDataMapper().updateById(wsData);
                } catch (Exception e) {
                    log.warn("消息:{}不存在", msgId);
//                    e.printStackTrace();
                }
            }
        }
    }
}
