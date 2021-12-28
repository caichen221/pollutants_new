package com.iscas.base.biz.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/28 9:58
 * @since jdk1.8
 */
@Configuration
@EnableConfigurationProperties(value = TokenProps.class)
public class TokenConfig {
    @Autowired
    private TokenProps tokenProps;
}
