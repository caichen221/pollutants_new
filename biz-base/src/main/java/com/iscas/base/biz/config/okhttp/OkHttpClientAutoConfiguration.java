package com.iscas.base.biz.config.okhttp;

import com.iscas.base.biz.service.common.OkHttpCustomClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * <p>okhttp 自动配置</p>
 **/
@Configuration//声明配置类
@EnableConfigurationProperties(OkHttpProps.class)
@ConditionalOnClass(OkHttpCustomClient.class)
@ConditionalOnProperty(prefix="okhttp",value="enabled",matchIfMissing=true)
@Slf4j
public class OkHttpClientAutoConfiguration {
    @Autowired
    private OkHttpProps okHttpConfig;

    @Bean
    @ConditionalOnMissingBean(OkHttpCustomClient.class)//容器中没有AuthorService的Bean的条件下配置该Bean
    public OkHttpCustomClient myOkHttpClient(){
        log.info("------初始化自定义的OkHttpClient--------");
        OkHttpCustomClient okHttpClient = new OkHttpCustomClient(okHttpConfig);
        log.info("------初始化自定义的OkHttpClient结束--------");
        return okHttpClient;
    }

}
