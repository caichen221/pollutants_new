package com.iscas.springboot.samples.http.message.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 19:57
 * @since jdk1.8
 */
@Configuration
public class HttpMessageConverterConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CustomHttpMessageConverter());
    }
}
