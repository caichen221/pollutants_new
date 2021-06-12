package com.iscas.samples.distributed.transaction.rocketmq.server2.controller;

import com.iscas.samples.distributed.transaction.rocketmq.server2.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/12 15:40
 * @since jdk1.8
 */
@RestController
@RequestMapping("/rocketmq/test")
public class StoreController {
    @Autowired
    private StoreService storeService;
    //主动查询下单结果
    @GetMapping(value = "/{id}")
    public String result(@PathVariable("id") int id) throws IOException {
        String result = storeService.queryOrderResult(id);
        return result;
    }

}

