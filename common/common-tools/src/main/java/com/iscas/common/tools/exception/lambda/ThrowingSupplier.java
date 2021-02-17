package com.iscas.common.tools.exception.lambda;

/**
 * Lambda表达式能够抛出异常的supplier
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/17 14:07
 * @since jdk1.8
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}