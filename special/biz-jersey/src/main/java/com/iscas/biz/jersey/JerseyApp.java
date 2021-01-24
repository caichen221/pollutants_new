package com.iscas.biz.jersey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 17:30
 * @since jdk1.8
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.iscas.biz.jersey"})
@Slf4j
public class JerseyApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JerseyApp.class);
        app.run(args);
        log.info("jersey服务已启动");
    }

}
