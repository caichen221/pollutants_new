package com.iscas.msi.server.samples.grpc.hello;

import com.iscas.msi.api.grpc.hello.HelloWorldGrpc;
import com.iscas.msi.api.grpc.hello.HelloWorldService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 17:45
 * @since jdk1.8
 */
@Slf4j
@Service
public class HelloServiceImpl extends HelloWorldGrpc.HelloWorldImplBase {
    @Override
    public void sayHello(HelloWorldService.HelloRequest request, StreamObserver<HelloWorldService.HelloResponse> responseObserver) {
//        super.sayHello(request, responseObserver);
        // 根据请求对象建立响应对象，返回响应信息
        HelloWorldService.HelloResponse response = HelloWorldService.HelloResponse
                .newBuilder()
                .setMessage(String.format("Hello, %s. This message comes from gRPC.", request.getName()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.debug("Client Message Received：[{}]", request.getName());
    }
}
