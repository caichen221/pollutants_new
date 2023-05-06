package com.iscas.check.referer.autoconfigure;

import com.iscas.check.referer.filter.RefererFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/8/7 13:51
 * @since jdk1.8
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
@Configuration
public class RefererFilterConfig {
    @Value("#{'${referer-allow-domains}'.split(',')}")
    private String[] allowDomains;
    @Bean
    public FilterRegistrationBean refereFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 4);
        filterRegistrationBean.setFilter(new RefererFilter(allowDomains));
        return filterRegistrationBean;
    }
}
