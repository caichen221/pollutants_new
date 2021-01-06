package com.iscas.common.tools.assertion;

import org.apache.commons.lang3.StringUtils;

/**
 * 对象断言
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:29
 * @since jdk1.8
 */
public class AssertObjUtils {
    private AssertObjUtils(){}

    /**
     * 断言obj的值不为空，如果为空，抛出异常，msg为异常信息
     * @version 1.0
     * @since jdk1.8
     * @date 2020/3/14
     * @param obj 待判断对象
     * @param msg 错误描述
     * @throws
     * @return void
     */
    public static void assertNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 断言obj的值为空，如果不为空，抛出异常，msg为异常信息
     * @version 1.0
     * @since jdk1.8
     * @date 2020/3/14
     * @param obj 待判断对象
     * @param msg 错误描述
     * @throws
     * @return void
     */
    public static void assertNull(Object obj, String msg) {
        if (obj != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 断言obj的值不为空字符串，如果为空字符串，抛出异常，msg为异常信息
     * @version 1.0
     * @since jdk1.8
     * @date 2020/3/14
     * @param obj 待判断对象
     * @param msg 错误描述
     * @throws
     * @return void
     */
    public static void assertNotEmpty(Object obj, String msg) {
        if (obj == null || StringUtils.isEmpty(obj.toString())) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 断言obj的值为空字符串，如果不为空字符串，抛出异常，msg为异常信息
     * @version 1.0
     * @since jdk1.8
     * @date 2020/3/14
     * @param obj 待判断对象
     * @param msg 错误描述
     * @throws
     * @return void
     */
    public static void assertEmpty(Object obj, String msg) {
        if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
            throw new AssertRuntimeException(msg);
        }
    }


}
