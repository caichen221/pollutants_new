package com.iscas.samples.distributed.transaction.rocketmq.server1.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;
import com.iscas.samples.distributed.transaction.rocketmq.server1.service.TransactionProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:56
 * @since jdk1.8
 */
@RestController
@RequestMapping("/rocketmq/server1")
public class OrderController {

    @Autowired
    private TransactionProducer transactionProducer;
    @Value("${rocketmq.topic}")
    private String rocketmqTopic;

    @GetMapping("/test")
    public String createOrder() throws MQClientException {
        Order order = new Order();
        order.setName("牛奶");
        order.setNum(2);
        order.setPrice(5.2f);
        order.setId(ThreadLocalRandom.current().nextInt(100000) + 1);

        //通过uuid 当key
        String uuid = UUID.randomUUID().toString().replace("_", "");

        //封装消息
        String s = JSON.toJSONString(order);

        //封装消息实体
        Message message = new Message(rocketmqTopic, null, uuid, s.getBytes());
        //发送消息 用 sendMessageInTransaction  第一个参数可以理解成消费方需要的参数 第二个参数可以理解成消费方不需要 本地事务需要的参数
        SendResult sendResult = transactionProducer.getProducer().sendMessageInTransaction(message, "");
        System.out.printf("发送结果=%s, sendResult=%s \n", sendResult.getSendStatus(), sendResult.toString());

        if (SendStatus.SEND_OK == sendResult.getSendStatus()) {
            return "成功";
        }
        return "失败";
    }
}
