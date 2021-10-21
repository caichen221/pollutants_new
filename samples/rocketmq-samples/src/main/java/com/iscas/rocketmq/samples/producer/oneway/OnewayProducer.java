package com.iscas.rocketmq.samples.producer.oneway;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 单项消息
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/7 22:05
 * @since jdk1.8
 */
public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.setProducerGroup("group");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("oneway-topic-1");
            message.setTags("oneway-tag-1");
            message.setBody(("hello" + i).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(message);
            TimeUnit.MICROSECONDS.sleep(100);
        }
        producer.shutdown();
    }
}
