package com.iscas.biz.mp.test.controller;

import com.iscas.biz.mp.test.model.TestEntity;
import com.iscas.biz.mp.test.service.mysql1.TestService1;
import com.iscas.biz.mp.test.service.mysql2.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 15:10
 * @since jdk1.8
 */
@RestController
public class TestMultiDbController {
    @Autowired
    private TestService1 testService1;
    @Autowired
    private TestService2 testService2;

    @GetMapping("/t11")
    public List<Map> test1() {
        return testService1.getIds();
    }

    @GetMapping("/t22")
    public List<Map> test2() {
        return testService2.getIds();
    }

    @GetMapping("/t33")
    public List<TestEntity> test3() {
        return testService1.get();
    }

    @GetMapping("/t44")
    public List<TestEntity> test4() {
        List<TestEntity> entities = testService1.get2();
        return entities;
    }
}
