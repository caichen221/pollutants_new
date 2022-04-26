package com.iscas.biz.flowable.enable;

import com.iscas.biz.flowable.configuration.FlowableConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 是否允许Flowable工作流引擎
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/26 18:01
 * @since jdk11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FlowableConfiguration.class)
public @interface EnableFlowable {
}
