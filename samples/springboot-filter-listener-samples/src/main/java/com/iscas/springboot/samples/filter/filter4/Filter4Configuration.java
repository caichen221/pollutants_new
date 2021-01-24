package com.iscas.springboot.samples.filter.filter4;

import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Filter4Configuration {

    @Bean
    public DelegatingFilterProxyRegistrationBean delegatingFilterProxyRegistrationBean(){
        DelegatingFilterProxyRegistrationBean filterProxy = new DelegatingFilterProxyRegistrationBean("filter4");
        filterProxy.addUrlPatterns("/*");
        filterProxy.setOrder(-5);
        return filterProxy;
    }
}