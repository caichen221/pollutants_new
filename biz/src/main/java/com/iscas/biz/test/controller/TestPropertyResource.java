package com.iscas.biz.test.controller;

import com.iscas.base.biz.config.property.MixPropertySourceFactory;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试@PropertyResource
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/21 21:37
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/property/resource")
@PropertySource(name = "test-property-source.yml", value = "classpath:test-property-source.yml", factory = MixPropertySourceFactory.class)
public class TestPropertyResource extends BaseController {

    @Value("${person.zhangsan.age}")
    private int age;

    @GetMapping
    public ResponseEntity testPropertyResource() {
        System.out.println("张三年龄：" + age);
        return getResponse();
    }
}
