package com.iscas.common.tools.assertion;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组断言
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 18:17
 * @since jdk1.8
 */
public class AssertArrayUtils {
    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static <T> void assertArrayNull(T[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(long[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(int[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(byte[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(short[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(char[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(boolean[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(float[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNull(double[] t, String msg) {
        if (t != null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static <T> void assertArrayNotNull(T[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(long[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(int[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(byte[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(short[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(char[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(boolean[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(float[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotNull(double[] t, String msg) {
        if (t == null) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static <T> void assertArrayEmpty(T[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(long[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(int[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(byte[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(short[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(char[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(boolean[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(float[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组为空，如果不为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayEmpty(double[] t, String msg) {
        if (ArrayUtils.isNotEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static <T> void assertArrayNotEmpty(T[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(long[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(int[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(byte[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(short[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(char[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(boolean[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(float[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }

    /**
     * 认为数组不为空，如果为空，抛出异常，msg为异常信息
     * str 判断的字符串
     * msg 抛出异常时的信息
     * */
    public static void assertArrayNotEmpty(double[] t, String msg) {
        if (ArrayUtils.isEmpty(t)) {
            throw new AssertRuntimeException(msg);
        }
    }
}
