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
public class AssertMapUtils {
    private AssertMapUtils(){}


    /**
     * 认为Map不为null，如果为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertMapNotNull(Map map, String msg) {
        if (map == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为null，如果为null，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertMapNull(Map map, String msg) {
        if (map != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertMapNotEmpty(Map map, String msg) {
        if (MapUtils.isEmpty(map)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为Map不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertMapEmpty(Map map, String msg) {
        if (MapUtils.isNotEmpty(map)) {
            throw new AssertRuntimeException(msg);
        }
    }
}
