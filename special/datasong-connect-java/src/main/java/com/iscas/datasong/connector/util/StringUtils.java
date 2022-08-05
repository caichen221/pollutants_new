package com.iscas.datasong.connector.util;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/29 16:17
 * @since jdk1.8
 */
public class StringUtils {
    public static int safeIntParse(String intAsString) {
        try {
            return Integer.parseInt(intAsString);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static boolean equalsAny(String str, String... strs) {
        return Arrays.asList(strs).contains(str);
    }

}
