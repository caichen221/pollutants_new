package com.iscas.biz.flowable.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.rest.service.api.RestResponseFactory;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 14:17
 * @since jdk11
 */
@SuppressWarnings(value = "unused")
@ComponentScan("org.flowable.rest.service.api")
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }

    @Autowired
    protected ObjectMapper objectMapper;

    @Bean()
    public RestResponseFactory restResponseFactory() {
        return new RestResponseFactory(objectMapper);
    }
}
