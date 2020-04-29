package com.iscas.base.biz.config.cros;

import com.iscas.base.biz.filter.CustomCrosFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableConfigurationProperties(CrosProps.class)
@ConditionalOnClass(CustomCrosFilter.class)
@ConditionalOnProperty(prefix = "cros",matchIfMissing = true,value = "enabled")
@Slf4j
public class CrosAutoConfiguration {
    @Autowired
    private CrosProps crosProps;
    @Bean
    @ConditionalOnMissingBean(CustomCrosFilter.class)
    public CustomCrosFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", null);
        return new CustomCrosFilter(source,crosProps);
    }

    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean crosFilterRegistrationBean() {
        log.info("-----注册跨域过滤器-------");
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setOrder(Ordered.HIGHEST_PRECEDENCE);
        frb.setFilter(corsFilter());
        frb.addUrlPatterns("/*");
        frb.setName("crosFilter");
        log.info("-----注册跨域过滤器结束-------");
        return frb;
    }
}
