package com.iscas.biz.socketio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NettySocketSpringApplication {




  public static void main(String[] args) { 
    SpringApplication.run(NettySocketSpringApplication.class, args); 
  } 
} 
