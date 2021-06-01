package com.iscas.samples.distributed.transaction.rocketmq.server2.mapper;


/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 16:04
 * @since jdk1.8
 */
public interface OrderMarkMapper {
    int countByOrderId(int orderId);
    int insertOrderMark(int orderId);
}
