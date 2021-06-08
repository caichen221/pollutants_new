package com.iscas.rocketmq.samples.producer.order;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 顺序生产者
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/8 14:01
 * @since jdk1.8
 */
public class OrderProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("172.16.10.169:9876");
        producer.start();
        List<Ticket> tickets = Ticket.createTickets();
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            String ticketJsonStr = JSONObject.toJSONString(ticket);
            Message message = new Message("order-topic-1", "order-tag-1", ticketJsonStr.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    int id = (int) arg;
                    return mqs.get(id % mqs.size());
                }
            }, ticket.getId());
            System.out.println("顺序生产者发送消息:" + sendResult);
        }
        producer.shutdown();

    }
}
