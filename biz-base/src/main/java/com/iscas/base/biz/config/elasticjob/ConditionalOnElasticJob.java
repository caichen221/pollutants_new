package com.iscas.base.biz.config.elasticjob;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/1 9:39
 * @since jdk1.8
 * */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnElasticJob.class)
public @interface ConditionalOnElasticJob {
}
