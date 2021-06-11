package com.iscas.samples.distributed.transaction.rocketmq.server2.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 15:21
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class OrderMark {
    private Integer orderId;
    private Date time;
}
