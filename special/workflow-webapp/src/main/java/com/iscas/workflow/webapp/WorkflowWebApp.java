package com.iscas.workflow.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/1/27 13:47
 * @since jdk1.8
 */
@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class} )
public class WorkflowWebApp {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowWebApp.class, args);
    }
}
