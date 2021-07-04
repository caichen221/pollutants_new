package com.iscas.base.biz.config.cas;

import com.iscas.base.biz.config.shedlock.OnShedLock;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/07/03
 * @since jdk1.8
 * */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnCustomCasClient.class)
public @interface ConditionalOnCustomCasClient {
}
