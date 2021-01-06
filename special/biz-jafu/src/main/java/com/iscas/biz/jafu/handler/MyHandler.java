package com.iscas.biz.jafu.handler;

import com.iscas.biz.jafu.service.TestService;
import com.iscas.biz.jafu.service.TestService2;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/5 22:07
 * @since jdk1.8
 */
public class MyHandler {
    private TestService testService;
    private TestService2 testService2;

    public MyHandler(TestService testService, TestService2 testService2) {
        this.testService = testService;
        this.testService2 = testService2;
    }

    public ServerResponse test(ServerRequest request) {
        return ServerResponse.ok().body(testService.test() + "_" + testService2.test2());
    }
}
