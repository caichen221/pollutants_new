package com.iscas.samples.distributed.transaction.rocketmq.server2.service;

import com.alibaba.fastjson.JSONObject;
import com.iscas.samples.distributed.transaction.rocketmq.server2.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 15:04
 * @since jdk1.8
 */
@Component
@Slf4j
public class OrderConsumer {
    private DefaultMQPushConsumer consumer;

    private String consumerGroup = "produce_consumer_group";

    public OrderConsumer(@Autowired StoreService storeService,
                         @Value("${rocketmq.nameserver}") String rocketmqNameserver,
                         @Value("${rocketmq.topic}") String rocketmqTopic) throws MQClientException {
        //设置消费组
        consumer = new DefaultMQPushConsumer(consumerGroup);
        // 添加服务器地址
        consumer.setNamesrvAddr(rocketmqNameserver);
        // 添加订阅号
        consumer.subscribe(rocketmqTopic, "*");
        // 监听消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt msg = msgs.get(0);
            String message = new String(msgs.get(0).getBody());
            Order order = JSONObject.parseObject(message, Order.class);
            String key = msg.getKeys();

            try {
                storeService.updateStore(order);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                log.info("消费失败，进行重试，重试到一定次数 那么将该条记录记录到数据库中，进行如果处理");
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        consumer.start();
        System.out.println("consumer start ...");
    }

}
