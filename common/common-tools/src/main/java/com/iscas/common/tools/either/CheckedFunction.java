package com.iscas.common.tools.either;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/18 15:20
 * @since jdk1.8
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}