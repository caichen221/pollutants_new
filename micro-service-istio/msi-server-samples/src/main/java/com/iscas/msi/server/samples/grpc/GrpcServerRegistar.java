package com.iscas.msi.server.samples.grpc;

import com.iscas.common.rpc.tools.grpc.server.GrpcServerUtils;
import com.iscas.msi.server.samples.grpc.hello.HelloServiceImpl;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * gRPC Server的配置——启动、关闭等
 * 需要使用<code>@Component</code>注解注册为一个Spring Bean
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 10:39
 * @since jdk1.8
 */
@Component
@Slf4j
public class GrpcServerRegistar {
    @Autowired
    private HelloServiceImpl service;

    /** 注入配置文件中的端口信息 */
    @Value("${grpc.server.port}")
    private int port;


    public void helloStart() throws IOException, InterruptedException {
        // 构建服务端
        log.info("Starting gRPC on port {}.", port);
        Server server = GrpcServerUtils.start(service, port);
        log.info("gRPC server started, listening on {}.", port);

        // 添加服务端关闭的逻辑
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down gRPC server.");
            try {
                GrpcServerUtils.shutdown(server);
            } catch (Throwable e) {
                log.warn("服务[{}]关闭失败", HelloServiceImpl.class.getSimpleName(), e);
            }
            log.info("gRPC server shut down successfully.");
        }));
        GrpcServerUtils.awaitTermination(server);

    }

}
