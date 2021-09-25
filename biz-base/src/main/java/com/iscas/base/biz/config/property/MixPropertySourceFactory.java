package com.iscas.base.biz.config.property;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

/**
 * 兼容配置，使得@PropertyResource注解也能支持yaml
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/21 21:24
 * @since jdk1.8
 */
public class MixPropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String sourceName = resource.getResource().getFilename();
        if (sourceName != null && (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml"))) {
            //将yaml文件转为properties
            return new PropertiesPropertySource(name, convert(resource));
        }
        return super.createPropertySource(name, resource);
    }

    private Properties convert(EncodedResource encodedResource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
