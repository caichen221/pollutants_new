package com.iscas.base.biz.aop.norepeat.submit;

import com.iscas.base.biz.config.norepeat.submit.NoRepeatSubmitLockType;

import java.lang.annotation.*;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:13
 * @since jdk1.8
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Documented
public @interface NoRepeatSubmit {

}
