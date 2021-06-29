package com.iscas.pap.pluggable.annotation;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/27 16:41
 * @since jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ThrowBaseException {
    Class[] exceptionClasses() default Exception.class;


    String[] msg() default "";
}
