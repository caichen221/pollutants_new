package com.iscas.rocketmq.samples.producer.sync;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 同步消息生产者
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/7 21:29
 * @since jdk1.8
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.setProducerGroup("group2");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("sync-topic-2");
            message.setTags("sync-tag-2");
            message.setBody(("hello" + i).getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message);
            SendStatus sendStatus = sendResult.getSendStatus();
            String msgId = sendResult.getMsgId();
            int queueId = sendResult.getMessageQueue().getQueueId();
            System.out.println("消息状态:" + sendStatus + ",消息ID:" + msgId + ",接收队列ID:" + queueId +
                    ",接收队列：" + sendResult.getMessageQueue());
            TimeUnit.MICROSECONDS.sleep(500);
        }
        producer.shutdown();
    }
}
