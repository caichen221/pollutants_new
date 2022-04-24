package com.iscas.biz.config;

import cn.hutool.core.util.ReflectUtil;
import com.iscas.biz.config.log.AccessLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/8/28 21:02
 * @since jdk1.8
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("asyncExecutor")
    private ThreadPoolTaskExecutor asyncExecutor;
    @Value("${response.header.server:newframe/2.0.0}")
    private String responseHeaderServer;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        registry.
                addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址
        registry.addInterceptor(new AccessLogInterceptor(responseHeaderServer))
                .addPathPatterns("/**")
                .excludePathPatterns("/404");
//                .excludePathPatterns("/login","/userLogin")
//                .excludePathPatterns("/image/**");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(asyncExecutor);
    }
}

