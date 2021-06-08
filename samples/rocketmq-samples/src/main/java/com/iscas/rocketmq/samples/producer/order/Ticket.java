package com.iscas.rocketmq.samples.producer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

/**
 * 示例车票
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/8 13:56
 * @since jdk1.8
 */
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private String from;
    private String to;
    private String status;

    public static List<Ticket> createTickets() {
        Ticket ticket1 = new Ticket(1, "北京", "上海", "下单");
        Ticket ticket2 = new Ticket(2, "哈尔滨", "广州", "下单");
        Ticket ticket3 = new Ticket(1, "北京", "上海", "支付");
        Ticket ticket4 = new Ticket(1, "北京", "上海", "出票");
        Ticket ticket5 = new Ticket(2, "哈尔滨", "广州", "支付");
        Ticket ticket6 = new Ticket(1, "北京", "上海", "增加积分");
        Ticket ticket7 = new Ticket(3, "天津", "甘肃", "下单");
        return Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5,
                ticket6, ticket7);
    }
}
