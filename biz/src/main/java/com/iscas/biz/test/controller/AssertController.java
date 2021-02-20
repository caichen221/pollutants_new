package com.iscas.biz.test.controller;

import com.iscas.common.tools.assertion.AssertObjUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:52
 * @since jdk1.8
 */
@RestController
@RequestMapping("/assert")
public class AssertController {

    @GetMapping("/test")
    public String test() {
        String str = null;
        AssertObjUtils.assertNotNull(str, "str 不能为null");
        return str;
    }
}
