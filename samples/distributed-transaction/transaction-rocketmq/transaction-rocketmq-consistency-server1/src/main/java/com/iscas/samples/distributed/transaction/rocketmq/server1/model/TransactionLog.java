package com.iscas.samples.distributed.transaction.rocketmq.server1.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:35
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class TransactionLog {
    private String id;
    private String business;
    private String foreignKey;
}
