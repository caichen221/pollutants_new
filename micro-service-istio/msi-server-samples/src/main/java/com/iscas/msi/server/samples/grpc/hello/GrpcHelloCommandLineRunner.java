package com.iscas.msi.server.samples.grpc.hello;

import com.iscas.msi.server.samples.grpc.GrpcServerRegistar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 加入gRPC Server的启动、停止等逻辑到Spring Boot的生命周期中
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 13:41
 * @since jdk1.8
 */
@Component
public class GrpcHelloCommandLineRunner implements CommandLineRunner {
    @Autowired
    GrpcServerRegistar serverRegistar;

    @Override
    public void run(String... args) throws Exception {
       serverRegistar.helloStart();
    }
}

