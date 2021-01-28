package com.iscas.msi.api;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 17:45
 * @since jdk1.8
 */
public class HelloServiceImpl extends HelloWorldGrpc.HelloWorldImplBase {
    @Override
    public void sayHello(HelloWorldService.HelloRequest request, StreamObserver<HelloWorldService.HelloResponse> responseObserver) {
        super.sayHello(request, responseObserver);
    }
}
