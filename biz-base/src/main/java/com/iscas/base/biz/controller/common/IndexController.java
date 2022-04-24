package com.iscas.base.biz.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/6/4 10:34
 * @since jdk1.8
 */
@SuppressWarnings("unused")
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
