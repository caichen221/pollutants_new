package com.iscas.msi.client.samples.service;

import com.iscas.common.rpc.tools.grpc.client.GrpcClientUtils;
import com.iscas.msi.api.grpc.hello.HelloWorldGrpc;
import com.iscas.msi.api.grpc.hello.HelloWorldService;
import com.iscas.msi.client.samples.grpc.GrpcClientRegistar;
import io.grpc.ManagedChannel;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 14:02
 * @since jdk1.8
 */
@Service
public class HelloService {
    @Autowired
    private GrpcClientRegistar clientRegistar;

    public String callSayHello(String name) {
        //客户端调用
        HelloWorldGrpc.HelloWorldBlockingStub psbs = clientRegistar.getHelloWorldStub();
        HelloWorldService.HelloRequest req = HelloWorldService.HelloRequest.newBuilder().setName(name).build();
        HelloWorldService.HelloResponse res = psbs.sayHello(req);
        return res.getMessage();
    }

}
