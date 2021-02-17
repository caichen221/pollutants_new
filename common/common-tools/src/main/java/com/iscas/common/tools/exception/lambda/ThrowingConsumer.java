package com.iscas.common.tools.exception.lambda;

/**
 * Lambda表达式能够抛出异常的consumer
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/17 14:07
 * @since jdk1.8
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {
    void accept(T t) throws Exception;
}