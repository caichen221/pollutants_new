package com.iscas.common.tools.assertion;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 断言
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:29
 * @since jdk1.8
 */
public class AssertStrUtils {
    private AssertStrUtils(){}


    /**
     * 认为str的字符串不为null，如果为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertStrNotNull(String str, String msg) {
        if (str == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为str的字符串为null，如果不为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertStrNull(String str, String msg) {
        if (str != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为str的字符串不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertStrNotEmpty(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为str的字符串为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertStrEmpty(String str, String msg) {
        if (StringUtils.isNotEmpty(str)) {
            throw new AssertRuntimeException(msg);
        }
    }
}
