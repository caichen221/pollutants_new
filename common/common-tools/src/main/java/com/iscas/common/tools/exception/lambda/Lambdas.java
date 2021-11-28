package com.iscas.common.tools.exception.lambda;

import java.util.function.*;

/**
 * Lambda表达式异常处理通用工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/17 14:07
 * @since jdk1.8
 */
public class Lambdas {


    /**
     * lambda consumer统一异常处理
     * @version 1.0
     * @since jdk1.8
     * @date 2021/2/17
     * @param consumer
     * @throws
     * @return java.util.function.Consumer<T>
     */
    public static <T> Consumer<T> wrappeConsumer(ThrowingConsumer<T> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * lambda BiConsumer统一异常处理
     * @version 1.0
     * @since jdk1.8
     * @date 2021/2/17
     * @param consumer
     * @throws
     * @return java.util.function.Consumer<T>
     */
    public static <T, U> BiConsumer<T, U> wrapperBiConsumer(ThrowingBiConsumer<T, U> consumer) {
        return (i, j) -> {
            try {
                consumer.accept(i, j);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * lambda function统一异常处理
     * @version 1.0
     * @since jdk1.8
     * @date 2021/2/17
     * @param function
     * @throws
     * @return java.util.function.Consumer<T>
     */
    public static <T, R> Function<T, R> wrapperFunction(ThrowingFunction<T, R> function) {
        return i -> {
            try {
                return function.apply(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * lambda supplier统一异常处理
     * @version 1.0
     * @since jdk1.8
     * @date 2021/2/17
     * @param supplier
     * @throws
     * @return java.util.function.Consumer<T>
     */
    public static <T> Supplier<T> wrapperSupplier(ThrowingSupplier<T> supplier) {
        return () -> {
            try {
                return (T) supplier.get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * lambda predicate统一异常处理
     * @version 1.0
     * @since jdk1.8
     * @date 2021/2/17
     * @param predicate
     * @throws
     * @return java.util.function.Consumer<T>
     */
    public static <T> Predicate<T> wrapperPredicate(ThrowingPredicate<T> predicate) {
        return i -> {
            try {
                return predicate.test(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    //    public static <T, E extends Exception> Consumer<T> lambdaRuntimeWrapper(Consumer<T> consumer, Class<E> clazz) {
//        return i -> {
//            try {
//                consumer.accept(i);
//            } catch (Exception ex) {
//                try {
//                    E exCast = clazz.cast(ex);
//                } catch (ClassCastException ccEx) {
//                    throw ex;
//                }
//            }
//        };
//    }
//
//    public static <T, E extends Exception> Consumer<T> lambdaWrapper(
//            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
//        return i -> {
//            try {
//                throwingConsumer.accept(i);
//            } catch (Exception ex) {
//                try {
//                    E exCast = exceptionClass.cast(ex);
//                } catch (ClassCastException ccEx) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        };
//    }
}
