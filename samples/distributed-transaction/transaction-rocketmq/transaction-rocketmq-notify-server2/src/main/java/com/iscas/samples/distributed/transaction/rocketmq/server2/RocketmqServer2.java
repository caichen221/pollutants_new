package com.iscas.samples.distributed.transaction.rocketmq.server2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 14:55
 * @since jdk1.8
 */
@SpringBootApplication
@MapperScan("com.iscas.samples.distributed.transaction.rocketmq.server2.mapper")
public class RocketmqServer2 {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqServer2.class, args);
    }
}
