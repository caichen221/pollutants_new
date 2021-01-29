package com.iscas.msi.client.samples.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 加入gRPC Client的启动、停止等逻辑到Spring Boot的生命周期中
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 13:41
 * @since jdk1.8
 */
@Component
public class GrpcHelloCommandLineRunner implements CommandLineRunner {
    @Autowired
    private GrpcClientRegistar clientRegistar;

    @Override
    public void run(String... args) throws Exception {
        clientRegistar.helloStart();
    }
}

