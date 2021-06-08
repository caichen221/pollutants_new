package com.iscas.rocketmq.samples.producer.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 发送延时消息
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/8 16:03
 * @since jdk1.8
 */
public class DelayProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("delay-topic-1", "delay-tag-1", ("msg" + i).getBytes(StandardCharsets.UTF_8));
            //支持得级别：messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(2);
            SendResult sendResult = producer.send(message);
            System.out.println("发送结果:" + sendResult);
        }
        producer.shutdown();
    }
}
