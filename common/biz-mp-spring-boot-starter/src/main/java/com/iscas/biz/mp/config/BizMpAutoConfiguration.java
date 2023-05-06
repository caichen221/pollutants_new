package com.iscas.biz.mp.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/5/6 16:01
 */
@ComponentScan("com.iscas.biz.mp")
@Configuration
@EnableAutoConfiguration(exclude = { MybatisAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
public class BizMpAutoConfiguration {
}
