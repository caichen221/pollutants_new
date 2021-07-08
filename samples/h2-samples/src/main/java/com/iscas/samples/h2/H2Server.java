package com.iscas.samples.h2;

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
public class H2Server {
    public static void main(String[] args) {
        SpringApplication.run(H2Server.class, args);
    }
}
