package com.iscas.common.tools.exception.lambda;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Lambda表达式能够抛出异常的Predicate
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/17 14:07
 * @since jdk1.8
 */
@FunctionalInterface
public interface ThrowingPredicate<T>{
    boolean test(T t) throws Exception;

    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> {
            try {
                return test(t) && other.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default Predicate<T> negate() {
        return (t) -> {
            try {
                return !test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> {
            try {
                return test(t) || other.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>)target.negate();
    }
}