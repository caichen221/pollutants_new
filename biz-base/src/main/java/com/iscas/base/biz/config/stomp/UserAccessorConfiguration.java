package com.iscas.base.biz.config.stomp;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/7 15:09
 * @since jdk1.8
 */
@Configuration
public class UserAccessorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserAccessor getUser(){
        return new DefaultUserAccessor();
    }
}
