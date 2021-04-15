package com.iscas.biz.mp.aop.associate;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/9/4 11:07
 * @since jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Deprecated
public @interface CustomResult {
    String table();
    String column();
    String alias() default "";
}
