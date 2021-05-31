package com.iscas.samples.distributed.transaction.seata.at.server2.controller;

import com.iscas.samples.distributed.transaction.seata.at.server2.mapper.UserDetailsMapper;
import com.iscas.samples.distributed.transaction.seata.at.server2.po.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:59
 * @since jdk1.8
 */
@RestController
@RequestMapping("/at/server2")
public class TestController {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsMapper userDetailsMapper;
//    @Transactional
    @GetMapping
    public String test1(Integer id) {
//        RootContext.bind(xid);
        UserDetails user = new UserDetails();
        user.setUserId(id);
        user.setDescription("1111");
        userDetailsMapper.insert(user);
        return "success";
    }
}
