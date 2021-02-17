package com.iscas.sso.mix.backend.samples.config;

import com.iscas.sso.mix.backend.filter.LoginFilter;
import com.iscas.sso.mix.backend.filter.MyPermissionHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 20:21
 * @since jdk1.8
 */
@Configuration
public class CommonConfig {
    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        frb.setFilter(new LoginFilter(new MyPermissionHandler(), "http://localhost:8965/"));
        frb.addUrlPatterns("/*");
        frb.setName("loginFilter");
        return frb;
    }
}
