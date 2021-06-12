package com.iscas.samples.distributed.transaction.rocketmq.server1.controller;

import com.iscas.samples.distributed.transaction.rocketmq.server1.model.Order;
import com.iscas.samples.distributed.transaction.rocketmq.server1.service.OrderService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/12 14:18
 * @since jdk1.8
 */
@RestController
@RequestMapping("/rocketmq/test")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //下单
    @GetMapping(value = "/orders")
    public String pay() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //生成id
        int id = ThreadLocalRandom.current().nextInt(800000) + 1;
        Order order = new Order();
        order.setName("牛奶");
        order.setNum(2);
        order.setPrice(5.2f);
        order.setId(id);
        orderService.createOrder(order, "1");
        return "sccess";
    }

    //查询充值结果
    @GetMapping(value = "/orders/result/{id}")
    public Order payresult(@PathVariable("id") int id){
        try {
            Order order = orderService.getOrder(id);
            return order;
        } catch (Exception e ) {
            return null;
        }
    }
}
