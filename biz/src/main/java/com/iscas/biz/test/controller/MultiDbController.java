package com.iscas.biz.test.controller;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.test.service.atomikos.AtomikosTestService;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/20 16:29
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/multi/db")
@ConditionalOnMybatis
public class MultiDbController {
    @Autowired
    private AtomikosTestService atomikosTestService;

    @GetMapping("/t1")
    public ResponseEntity t1() {
        atomikosTestService.test();
        return new ResponseEntity<>();
    }
}
