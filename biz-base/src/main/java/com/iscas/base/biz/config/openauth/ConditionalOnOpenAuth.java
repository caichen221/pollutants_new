package com.iscas.base.biz.config.openauth;

import com.iscas.base.biz.config.elasticjob.v3.OnElasticJob;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/22 14:56
 * @since jdk1.8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnElasticJob.class)
public @interface ConditionalOnOpenAuth {
}
