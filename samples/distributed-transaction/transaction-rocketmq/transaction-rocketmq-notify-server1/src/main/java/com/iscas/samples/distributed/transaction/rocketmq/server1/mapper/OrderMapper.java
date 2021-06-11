package com.iscas.samples.distributed.transaction.rocketmq.server1.mapper;

import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 9:04
 * @since jdk1.8
 */
public interface OrderMapper {
    int insert(Order order);
}
