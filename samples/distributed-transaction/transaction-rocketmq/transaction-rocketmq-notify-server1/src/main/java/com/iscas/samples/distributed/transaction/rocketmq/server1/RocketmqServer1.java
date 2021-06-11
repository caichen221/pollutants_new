package com.iscas.samples.distributed.transaction.rocketmq.server1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 8:51
 * @since jdk1.8
 */
@SpringBootApplication
@MapperScan("com.iscas.samples.distributed.transaction.rocketmq.server1.mapper")
public class RocketmqServer1 {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqServer1.class, args);
    }
}
