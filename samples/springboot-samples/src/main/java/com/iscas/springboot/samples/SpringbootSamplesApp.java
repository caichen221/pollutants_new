package com.iscas.springboot.samples;

import com.iscas.springboot.samples.importselector.CacheType;
import com.iscas.springboot.samples.importselector.EnableCachex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 14:02
 * @since jdk1.8
 */
@SpringBootApplication
@EnableCachex(type = CacheType.REDIS)
public class SpringbootSamplesApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSamplesApp.class, args);
    }
}
