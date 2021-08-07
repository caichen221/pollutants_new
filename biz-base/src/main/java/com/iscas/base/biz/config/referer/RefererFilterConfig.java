package com.iscas.base.biz.config.referer;

import com.iscas.base.biz.filter.RefererFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/7 13:51
 * @since jdk1.8
 */
public class RefererFilterConfig {
    @Bean
    public FilterRegistrationBean refereFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 4);
        filterRegistrationBean.setFilter(new RefererFilter());
        return filterRegistrationBean;
    }
}
