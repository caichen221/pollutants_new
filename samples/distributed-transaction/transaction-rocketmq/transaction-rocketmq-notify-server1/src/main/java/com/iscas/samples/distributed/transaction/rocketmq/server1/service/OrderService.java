package com.iscas.samples.distributed.transaction.rocketmq.server1.service;

import com.alibaba.fastjson.JSON;
import com.iscas.samples.distributed.transaction.rocketmq.server1.mapper.OrderMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server1.mapper.TransactionLogMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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
    @Value("${rocketmq.nameserver}")
    private String nameserver;
    @Value("${rocketmq.topic}")
    private String topic;

    public Order createOrder(Order order, String transactionId) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        orderMapper.insert(order);
        DefaultMQProducer producer = new DefaultMQProducer("rocket-notify");
        producer.setNamesrvAddr(nameserver);
        producer.start();
        Message message = new Message(topic, JSON.toJSONString(order).getBytes(StandardCharsets.UTF_8));
        SendResult send = producer.send(message);
        producer.shutdown();
//        TransactionLog transactionLog = new TransactionLog();
//        transactionLog.setId(transactionId).setBusiness("Order")
//                .setForeignKey(String.valueOf(order.getId()));
//        transactionLogMapper.insert(transactionLog);
        return order;
    }

    //查询订单结果
    public Order getOrder(int orderId) {
        Order order = orderMapper.selectById(orderId);
        return order;
    }
}
