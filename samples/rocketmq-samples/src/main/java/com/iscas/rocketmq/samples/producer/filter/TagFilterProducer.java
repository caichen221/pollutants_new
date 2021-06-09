package com.iscas.rocketmq.samples.producer.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 使用TAG过滤生产者
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/9 20:28
 * @since jdk1.8
 */
public class TagFilterProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("tag-filter-topic", "tag-filter-tag-1", ("msg-tag1:" + i).getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message);
            System.out.println("发送结果:" + sendResult);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message("tag-filter-topic", "tag-filter-tag-2", ("msg-tag2:" + i).getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message);
            System.out.println("发送结果:" + sendResult);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message("tag-filter-topic", "tag-filter-tag-3", ("msg-tag3:" + i).getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message);
            System.out.println("发送结果:" + sendResult);
        }
        producer.shutdown();
    }
}
