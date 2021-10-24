package com.iscas.springboot.samples.importselector;

import org.springframework.cache.Cache;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.text.MessageFormat;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/24 12:38
 * @since jdk1.8
 */
public class CacheSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableCachex.class.getName());
        CacheType type = (CacheType) annotationAttributes.get("type");
        switch (type) {
            case MEMORY: {
                return new String[]{MemoryCache.class.getName()};
            }
            case REDIS: {
                return new String[]{RedisCache.class.getName()};
            }
            default: {
                throw new RuntimeException(MessageFormat.format("unsupport cache type {0}", type.toString()));
            }
        }
    }
}
