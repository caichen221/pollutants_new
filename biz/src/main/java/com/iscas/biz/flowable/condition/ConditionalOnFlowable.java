package com.iscas.biz.flowable.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/26 18:08
 * @since jdk11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnFlowable.class)
public @interface ConditionalOnFlowable {
}
