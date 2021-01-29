package com.iscas.msi.client.samples.controller;

import com.iscas.msi.client.samples.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端调用
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/01/29 13:58
 * @since jdk1.8
 */
@RestController
@Slf4j
public class HelloController {
   @Autowired
   private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "啦啦啦", required = false) String name) {
        return helloService.callSayHello(name);
    }
}

