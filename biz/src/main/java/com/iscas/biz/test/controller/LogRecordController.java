package com.iscas.biz.test.controller;

import com.iscas.biz.config.log.LogRecord;
import com.iscas.biz.config.log.LogType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/2/23 11:36
 * @since jdk1.8
 */
@RestController
@RequestMapping("/logRecord")
public class LogRecordController {

    @LogRecord(type = LogType.AUTH, desc = "测试测试")
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
