package com.iscas.common.tools.assertion;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:29
 * @since jdk1.8
 */
public class AssertCollectionUtils {
    private AssertCollectionUtils(){}


    /**
     * 认为集合不为null，如果为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertCollectionNotNull(Collection collection, String msg) {
        if (collection == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为null，如果为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertCollectionNull(Collection collection, String msg) {
        if (collection != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertCollectionNotEmpty(Collection collection, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertMapEmpty(Collection collection, String msg) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new AssertRuntimeException(msg);
        }
    }
}
