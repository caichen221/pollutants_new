package com.iscas.base.biz.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.iscas.base.biz.aop.enable.EnableSpringBootAdminClient;
import de.codecentric.boot.admin.client.config.SpringBootAdminClientAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/3 18:09
 * @since jdk1.8
 */
public class MyExclusionFilter implements AutoConfigurationImportFilter, BeanFactoryAware {
    private BeanFactory beanFactory;
    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < matches.length; i++) {
            //如果是Springboot-admin-client的自动配置类，查看是否允许使用
            if (Objects.equals(SpringBootAdminClientAutoConfiguration.class.getName(),
                    autoConfigurationClasses[i])) {
                try {
                    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
                    Map<String, Object> beansWithAnnotation = defaultListableBeanFactory.getBeansWithAnnotation(EnableSpringBootAdminClient.class);
                    matches[i] = CollectionUtil.isNotEmpty(beansWithAnnotation);
                } catch (Exception e) {
                    System.err.println("查找EnableSpringBootAdminClient注解出错");
                    matches[i] = true;
                }

            } else {
                matches[i] = true;
            }
        }
        return matches;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
