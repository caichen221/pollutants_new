package com.iscas.rocketmq.samples.producer.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量发送消息
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/8 17:18
 * @since jdk1.8
 */
public class BatchProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.start();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("batch-topic-1", "batch-tag-1", ("msg" + i).getBytes(StandardCharsets.UTF_8));
            messageList.add(message);
        }
        producer.send(messageList);
        System.out.println("发送成功!!!");
        producer.shutdown();
    }
}
