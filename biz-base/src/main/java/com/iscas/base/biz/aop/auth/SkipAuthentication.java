package com.iscas.base.biz.aop.auth;

import java.lang.annotation.*;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/28 18:41
 * @since jdk1.8
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SkipAuthentication {
}
