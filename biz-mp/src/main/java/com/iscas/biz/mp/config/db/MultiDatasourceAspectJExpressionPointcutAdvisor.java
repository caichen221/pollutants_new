package com.iscas.biz.mp.config.db;

import cn.hutool.core.collection.CollectionUtil;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.aop.enable.EnableShardingJdbc;
import com.iscas.biz.mp.interfaces.IShardingJdbcHandler;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/4 18:17
 * @since jdk1.8
 * 多数据源切换的切面
 */
//@Configuration
@Slf4j
public class MultiDatasourceAspectJExpressionPointcutAdvisor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, Ordered, ApplicationContextAware {

    private Environment env;
    @Autowired
    private ApplicationContext context;

    public AspectJExpressionPointcutAdvisor getAspectJExpressionPointcutAdvisor(String pointcut, String dbType) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice((MethodInterceptor) invocation -> {
            try {
                log.debug("正在访问{}数据源...", dbType);
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
        if (dbNames != null) {
            String[] names = dbNames.split(",");
            if (ArrayUtils.isNotEmpty(names) && names.length > 1) {
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
        }

        //配置shardingjdbc数据源的切面
        Map<String, Object> enableShardingJdbcMap = context.getBeansWithAnnotation(EnableShardingJdbc.class);
        if (CollectionUtil.isNotEmpty(enableShardingJdbcMap)) {
            IShardingJdbcHandler shardingJdbcHandler = context.getBean(IShardingJdbcHandler.class);
            if (shardingJdbcHandler != null) {
                shardingJdbcHandler.registShardingAspect(registry, MultiDatasourceAspectJExpressionPointcutAdvisor.this);
            }
        }
//        String shardingNames = env.getProperty("spring.datasource.sharding.names");
//        String shardingPointcutNames = String.join(".", shardingNames.split(","));
//        String key = "spring.datasource.sharding." + shardingPointcutNames + ".pointcut";
//        String shardingDbName = String.join("_", shardingNames.split(","));
//        String value = env.getProperty(key);
//        if (StringUtils.isNotBlank(value)) {
//            AbstractBeanDefinition definition = BeanDefinitionBuilder
//                    .genericBeanDefinition(AspectJExpressionPointcutAdvisor.class, () -> getAspectJExpressionPointcutAdvisor(value, shardingDbName))
//                    .getBeanDefinition();
//            registry.registerBeanDefinition(AspectJExpressionPointcutAdvisor.class.getSimpleName() + shardingDbName, definition);
//        } else {
//            log.error("{}数据源没有配置切面的目录", shardingDbName);
//        }
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
