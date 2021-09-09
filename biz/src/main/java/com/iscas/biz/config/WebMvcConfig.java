package com.iscas.biz.config;

import com.iscas.biz.config.log.AccessLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/8/28 21:02
 * @since jdk1.8
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("asyncExecutor")
    private ThreadPoolTaskExecutor asyncExecutor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址
        registry.addInterceptor(new AccessLogInterceptor())
                .addPathPatterns("/**");
//                .excludePathPatterns("/login","/userLogin")
//                .excludePathPatterns("/image/**");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(asyncExecutor);
    }
}

