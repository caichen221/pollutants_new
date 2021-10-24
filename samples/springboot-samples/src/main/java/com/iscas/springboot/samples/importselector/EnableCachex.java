package com.iscas.springboot.samples.importselector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/24 12:34
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheSelector.class)
public @interface EnableCachex {
    CacheType type() default CacheType.MEMORY;
}
