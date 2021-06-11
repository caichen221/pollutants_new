package com.iscas.samples.distributed.transaction.rocketmq.server2.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 9:00
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class Order {

    private Integer id;
    private String name;
    private Integer num = 0;
    private float price;
}
