package com.iscas.sso.mix.ticket.share.agent.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/14 11:19
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class CommonTicketEntity {
    private String ticket; //ticket
    private String domain = "default"; //域名,默认使用default
    private int expire = 24 * 60; //过期时间，单位分钟
}
