package com.iscas.samples.distributed.transaction.seata.at.server2;

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
@SpringBootApplication/*(exclude = DataSourceAutoConfiguration.class)*/
@MapperScan(basePackages = "com.iscas.samples.distributed.transaction.seata.at.server2.mapper")
public class SeataServerAt2App {
    public static void main(String[] args) {
        SpringApplication.run(SeataServerAt2App.class, args);
    }
}
