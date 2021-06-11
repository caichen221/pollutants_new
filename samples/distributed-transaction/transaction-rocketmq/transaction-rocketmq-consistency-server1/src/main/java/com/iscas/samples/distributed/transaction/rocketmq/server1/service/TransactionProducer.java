package com.iscas.samples.distributed.transaction.rocketmq.server1.service;

import com.alibaba.fastjson.JSONObject;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:21
 * @since jdk1.8
 */

@Component
public class TransactionProducer {

    /**
     * 需要自定义事务监听器 用于 事务的二次确认 和 事务回查
     */
    private TransactionListener transactionListener;

    /**
     * 这里的生产者和之前的不一样
     */
    private TransactionMQProducer producer = null;

    /**
     * 官方建议自定义线程 给线程取自定义名称 发现问题更好排查
     */
    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        }

    });

    public TransactionProducer(@Autowired OrderService orderService, @Autowired
                                TransactionLogService transactionLogService,
                               @Value("${rocketmq.nameserver}") String rocketmqNameserver,
                               @Value("${rocketmq.topic}") String rocketmqTopic) {
        transactionListener = new TransactionListenerImpl(orderService, transactionLogService);
        // 初始化 事务生产者
        producer = new TransactionMQProducer(rocketmqTopic);
        // 添加服务器地址
        producer.setNamesrvAddr(rocketmqNameserver);
        // 添加事务监听器
        producer.setTransactionListener(transactionListener);
        // 添加自定义线程池
        producer.setExecutorService(executorService);
        producer.setSendMsgTimeout(30000);

        start();
    }

    public TransactionMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }
}

/**
 * @author xub
 * @Description: 自定义事务监听器
 * @date 2019/7/15 下午12:20
 */
@Slf4j
class TransactionListenerImpl implements TransactionListener {

    private OrderService orderService;
    private TransactionLogService transactionLogService;

    public TransactionListenerImpl(OrderService orderService, TransactionLogService transactionLogService) {
        this.orderService = orderService;
        this.transactionLogService = transactionLogService;
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("=========本地事务开始执行=============");
        try {
            String message = new String(msg.getBody());
            Order order = JSONObject.parseObject(message, Order.class);
            //模拟执行本地事务begin=======
            /**
             * 本地事务执行会有三种可能
             * 1、commit 成功
             * 2、Rollback 失败
             * 3、网络等原因服务宕机收不到返回结果
             */
            orderService.createOrder(order, msg.getTransactionId());
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 只有上面接口返回 LocalTransactionState.UNKNOW 才会调用查接口被调用
     *
     * @param msg 消息
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("==========回查接口=========");
        String transactionId = msg.getTransactionId();
        //1、必须根据key先去检查本地事务消息是否完成。
        Integer count = null;
        try {
            count = transactionLogService.getCountById(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        /**
         * 因为有种情况就是：上面本地事务执行成功了，但是return LocalTransactionState.COMMIT_MESSAG的时候
         * 服务挂了，那么最终 Brock还未收到消息的二次确定，还是个半消息 ，所以当重新启动的时候还是回调这个回调接口。
         * 如果不先查询上面本地事务的执行情况 直接在执行本地事务，那么就相当于成功执行了两次本地事务了。
         */
        //2、这里返回要么commit 要么rollback。没有必要在返回 UNKNOW
        return count > 0 ? LocalTransactionState.COMMIT_MESSAGE :
                LocalTransactionState.ROLLBACK_MESSAGE;
    }
}