package com.iscas.samples.distributed.transaction.seata.server1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:17
 * @since jdk1.8
 */
@SpringBootApplication/*(exclude = DataSourceAutoConfiguration.class)*/
@MapperScan(basePackages = "com.iscas.samples.distributed.transaction.seata.server1.mapper")

public class SeataServer1App {
    public static void main(String[] args) {
        SpringApplication.run(SeataServer1App.class, args);
    }
}
