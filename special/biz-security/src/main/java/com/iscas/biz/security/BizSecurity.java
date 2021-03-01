package com.iscas.biz.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/27 15:09
 * @since jdk1.8
 */
@SpringBootApplication
public class BizSecurity {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BizSecurity.class);
        springApplication.run(args);
    }
}
