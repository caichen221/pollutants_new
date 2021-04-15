package com.iscas.biz.mp.config.db;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/4 18:17
 * @since jdk1.8
 * 多数据源切换的切面
 */
//@Configuration
@Slf4j
@ConditionalOnMybatis
public class MultiDatasourceAspectJExpressionPointcutAdvisor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, Ordered {

    private Environment env;

    public AspectJExpressionPointcutAdvisor getAspectJExpressionPointcutAdvisor(String pointcut, String dbType) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice((MethodInterceptor) invocation -> {
            try {
                log.info("正在访问{}数据源...", dbType);
                DbContextHolder.setDbType(dbType);
                return invocation.proceed();
            } finally {
                DbContextHolder.clearDbType();
            }
        });
        return advisor;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String dbNames = env.getProperty("spring.datasource.names");
        String[] names = dbNames.split(",");
        //单数据源无需处理
        if (names.length <= 1) {
            return;
        }
        Arrays.stream(names).skip(1).forEach(name -> {
            String key = "spring.datasource.druid." + name + ".pointcut";
            String value = env.getProperty(key);
            if (StringUtils.isNotBlank(value)) {
                AbstractBeanDefinition definition = BeanDefinitionBuilder
                        .genericBeanDefinition(AspectJExpressionPointcutAdvisor.class, () -> getAspectJExpressionPointcutAdvisor(value, name))
                        .getBeanDefinition();
                registry.registerBeanDefinition(AspectJExpressionPointcutAdvisor.class.getSimpleName() + name, definition);
            } else {
                log.error("{}数据源没有配置切面的目录", name);
            }
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
