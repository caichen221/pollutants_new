package com.iscas.samples.distributed.transaction.seata.tcc.server2.controller;

import com.iscas.samples.distributed.transaction.seata.tcc.server2.po.UserDetails;
import com.iscas.samples.distributed.transaction.seata.tcc.server2.tcc.TestTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 13:40
 * @since jdk1.8
 */
@RestController
@RequestMapping("/tcc/server2")
public class TestController {
    @Autowired
    private TestTccAction testTccAction;

    @GetMapping
    public String test() {
        UserDetails userDetails = new UserDetails();
        userDetails.setDescription("xxxxxx");
        userDetails.setUserId(ThreadLocalRandom.current().nextInt(60000) + 1);
        testTccAction.prepareTest(null, userDetails);
        return "success";
    }
}
