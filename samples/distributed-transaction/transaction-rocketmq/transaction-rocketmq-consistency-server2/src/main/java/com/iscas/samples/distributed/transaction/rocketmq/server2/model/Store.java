package com.iscas.samples.distributed.transaction.rocketmq.server2.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 9:01
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class Store {
    private Integer id;
    private Integer stock;
    private String name;
}
