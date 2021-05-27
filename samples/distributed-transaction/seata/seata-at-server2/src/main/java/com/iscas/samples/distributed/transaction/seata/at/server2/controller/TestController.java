package com.iscas.samples.distributed.transaction.seata.at.server2.controller;

import com.iscas.samples.distributed.transaction.seata.at.server2.po.UserDetails;
import com.iscas.samples.distributed.transaction.seata.at.server2.mapper.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:59
 * @since jdk1.8
 */
@RestController
@RequestMapping("/server2")
public class TestController {
    @Autowired
    private UserDetailsMapper userDetailsMapper;
    @Transactional
    @GetMapping
    public String test1() {
        UserDetails user = new UserDetails();
        user.setUserId(11111);
        user.setDescription("1111");
        userDetailsMapper.insert(user);
        return "success";
    }
}
