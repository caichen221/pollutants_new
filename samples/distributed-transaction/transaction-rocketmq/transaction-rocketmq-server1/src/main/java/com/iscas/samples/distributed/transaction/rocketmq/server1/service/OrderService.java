package com.iscas.samples.distributed.transaction.rocketmq.server1.service;

import com.iscas.samples.distributed.transaction.rocketmq.server1.mapper.OrderMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server1.mapper.TransactionLogMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.TransactionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:35
 * @since jdk1.8
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order, String transactionId) {
        orderMapper.insert(order);
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(transactionId).setBusiness("Order")
                .setForeignKey(String.valueOf(order.getId()));
        transactionLogMapper.insert(transactionLog);
    }
}
