package com.iscas.msi.client.samples.grpc;

import com.iscas.common.rpc.tools.grpc.client.GrpcClientUtils;
import com.iscas.common.rpc.tools.grpc.server.GrpcServerUtils;
import com.iscas.msi.api.grpc.hello.HelloWorldGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * gRP Client——启动、关闭等
 * 需要使用<code>@Component</code>注解注册为一个Spring Bean
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 10:39
 * @since jdk1.8
 */
@Component
@Slf4j
public class GrpcClientRegistar {
    @Value("${grpc.server.host}")
    private String host;
    @Value("${grpc.server.port}")
    private int port;
    private HelloWorldGrpc.HelloWorldBlockingStub psbs;


    public void helloStart() {
        //客户端调用
        ManagedChannel managedChannel = GrpcClientUtils.getManagedChannel(host, port);
        psbs = HelloWorldGrpc.newBlockingStub(managedChannel);
        // 添加客户端关闭的逻辑
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                GrpcClientUtils.shutdown(managedChannel);
            } catch (InterruptedException e) {
                log.warn("关闭连接出错", e);
            }
        }));
    }

    public HelloWorldGrpc.HelloWorldBlockingStub getHelloWorldStub() {
        return psbs;
    }

}
