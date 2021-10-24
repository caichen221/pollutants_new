package com.iscas.springboot.samples.importselector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/24 12:49
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/cache")
public class CacheTestController {
    @Autowired
    private ICache cache;

    @GetMapping
    public String test() {
        return cache.test();
    }
}
