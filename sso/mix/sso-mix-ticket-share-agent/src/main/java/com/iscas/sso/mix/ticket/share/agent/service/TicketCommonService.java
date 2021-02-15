package com.iscas.sso.mix.ticket.share.agent.service;

import com.iscas.sso.mix.ticket.share.agent.model.CommonTicketEntity;
import com.iscas.sso.mix.ticket.share.agent.util.LocalCache;

/**
 * 通用的ticket处理类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/14 13:47
 * @since jdk1.8
 */
public class TicketCommonService {
    public void storeTicket(CommonTicketEntity commonTicketEntity) {
        LocalCache.set(commonTicketEntity.getDomain(), commonTicketEntity.getTicket(),
                commonTicketEntity.getExpire() * 60000);
    }

    public String getTicket(String domain) {
        return (String) LocalCache.get(domain);
    }
}
