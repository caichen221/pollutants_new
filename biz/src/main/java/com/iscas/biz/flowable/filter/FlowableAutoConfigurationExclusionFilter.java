package com.iscas.biz.flowable.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.iscas.biz.flowable.enable.EnableFlowable;
import org.flowable.spring.boot.*;
import org.flowable.spring.boot.actuate.info.FlowableInfoAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration;
import org.flowable.spring.boot.cmmn.CmmnEngineAutoConfiguration;
import org.flowable.spring.boot.cmmn.CmmnEngineServicesAutoConfiguration;
import org.flowable.spring.boot.content.ContentEngineAutoConfiguration;
import org.flowable.spring.boot.content.ContentEngineServicesAutoConfiguration;
import org.flowable.spring.boot.dmn.DmnEngineAutoConfiguration;
import org.flowable.spring.boot.dmn.DmnEngineServicesAutoConfiguration;
import org.flowable.spring.boot.eventregistry.EventRegistryAutoConfiguration;
import org.flowable.spring.boot.eventregistry.EventRegistryServicesAutoConfiguration;
import org.flowable.spring.boot.form.FormEngineAutoConfiguration;
import org.flowable.spring.boot.form.FormEngineServicesAutoConfiguration;
import org.flowable.spring.boot.idm.IdmEngineAutoConfiguration;
import org.flowable.spring.boot.idm.IdmEngineServicesAutoConfiguration;
import org.flowable.spring.boot.ldap.FlowableLdapAutoConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.Map;
import java.util.Objects;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/5/30 9:39
 * @since jdk11
 */
@SuppressWarnings("unused")
public class FlowableAutoConfigurationExclusionFilter implements AutoConfigurationImportFilter, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < matches.length; i++) {
            if (Objects.equals(FlowableInfoAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(EndpointAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(RestApiAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(AppEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(AppEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(ProcessEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(ProcessEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(FlowableJpaAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(FormEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(FormEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(ContentEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(ContentEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(DmnEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(DmnEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(IdmEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(IdmEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(EventRegistryAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(EventRegistryServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(CmmnEngineAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(CmmnEngineServicesAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(FlowableLdapAutoConfiguration.class.getName(), autoConfigurationClasses[i]) ||
                    Objects.equals(FlowableSecurityAutoConfiguration.class.getName(), autoConfigurationClasses[i])) {
                //如果是Springboot-admin-client的自动配置类，查看是否允许使用
                try {
                    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
                    Map<String, Object> beansWithAnnotation = defaultListableBeanFactory.getBeansWithAnnotation(EnableFlowable.class);
                    matches[i] = CollectionUtil.isNotEmpty(beansWithAnnotation);
                } catch (Exception e) {
                    System.err.println("查找EnableFlowable注解出错");
                    matches[i] = false;
                }
            } else {
                matches[i] = true;
            }
        }
        return matches;
    }

}
