package com.iscas.biz.flowable.configuration;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 14:17
 * @since jdk11
 */
@Configuration
@SuppressWarnings(value = "unused")
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }
}
