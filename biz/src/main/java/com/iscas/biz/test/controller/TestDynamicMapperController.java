package com.iscas.biz.test.controller;

import com.iscas.biz.domain.common.User;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/4 16:55
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/dynamic/mapper")
@ConditionalOnMybatis
public class TestDynamicMapperController extends BaseController {
    @Autowired
    private DynamicMapper dynamicMapper;
    @GetMapping
    public ResponseEntity test() {
        ResponseEntity response = getResponse();
        List<User> users = dynamicMapper.select("select * from user_info", User.class);
        response.setValue(users);
        return response;
    }
}
