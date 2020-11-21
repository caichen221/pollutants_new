package com.iscas.biz.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/20 10:18
 * @since jdk1.8
 */
@Service
public class RabbitQueueService {
    @Autowired
    @Qualifier("rabbitConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AmqpTemplate amqpTemplate;
    public synchronized void checkAndCreateQueue(String queueName) throws IOException {
        try {
            //从spring中查看是否已经存在此队列对象，如果没有重新创建
            Object bean = applicationContext.getBean(queueName);
        } catch (Exception e) {
            connectionFactory.createConnection().createChannel(false).queueDeclare(queueName, true, false, false, null);
        }
    }
}
