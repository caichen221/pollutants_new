package com.iscas.samples.distributed.transaction.seata.server2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:17
 * @since jdk1.8
 */
@SpringBootApplication
@MapperScan(basePackages = "com.iscas.samples.distributed.transaction.seata.server2.mapper")
public class SeataServer2App {
    public static void main(String[] args) {
        SpringApplication.run(SeataServer2App.class, args);
    }
}
