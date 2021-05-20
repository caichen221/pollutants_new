package com.iscas.biz.mp.aop.enable;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/20 9:42
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(value={AtomikosConfiguration.class})
public @interface EnableAtomikos {
}
