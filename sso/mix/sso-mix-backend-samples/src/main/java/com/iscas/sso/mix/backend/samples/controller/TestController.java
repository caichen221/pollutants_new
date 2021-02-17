package com.iscas.sso.mix.backend.samples.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 10:16
 * @since jdk1.8
 */
@RestController
@CrossOrigin
public class TestController {
    @PostMapping("/greet")
    public String greet(@RequestBody Map map) {
        System.out.println(map);
        return "lalala";
    }
}
