package com.iscas.sso.mix.authentication.service;

import com.iscas.common.rpc.tools.grpc.client.GrpcClientUtils;
import com.iscas.sso.mix.auth.model.ResponseEntity;
import com.iscas.sso.mix.auth.model.UserEntity;
import com.iscas.sso.mix.auth.service.AuthGrpc;
import io.grpc.ManagedChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 13:22
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class GrpcClientTests {
    @Test
    public void test() {
        //客户端调用
        ManagedChannel managedChannel = GrpcClientUtils.getManagedChannel("127.0.0.1", 8964);
        AuthGrpc.AuthBlockingStub authBlockingStub = AuthGrpc.newBlockingStub(managedChannel);
        UserEntity.User user = UserEntity.User.newBuilder().setUsername("111").setPassword("1111").build();
        ResponseEntity.ResEntity resEntity = authBlockingStub.login(user);
        System.out.println(resEntity);
        managedChannel.shutdown();
    }

    @Test
    public void test2() {
        //客户端调用
        ManagedChannel managedChannel = GrpcClientUtils.getManagedChannel("127.0.0.1", 8964);
        AuthGrpc.AuthBlockingStub authBlockingStub = AuthGrpc.newBlockingStub(managedChannel);
        UserEntity.User user = UserEntity.User.newBuilder().setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRlIjoxNjEzNTM5MDE5LCJleHAiOjE2MTM2MjU0MTksImlhdCI6MTYxMzUzOTAxOSwidXNlcm5hbWUiOiIxMTEifQ.w9aqoWdn8WRw_f0CU6f_YKWtvYtsGKqiwXWubA07No4").build();
        ResponseEntity.ResEntity resEntity = authBlockingStub.verify(user);
        System.out.println(resEntity);
        managedChannel.shutdown();
    }

}
