package com.iscas.springboot.samples.filter.filter3;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 20:40
 * @since jdk1.8
 */
@Configuration
public class Filter3Configuration {
    @Bean
    public FilterRegistrationBean<Filter3> myFilterFilterRegistrationBean() {
        FilterRegistrationBean<Filter3> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filter3());
        registrationBean.setOrder(-1);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }
}
