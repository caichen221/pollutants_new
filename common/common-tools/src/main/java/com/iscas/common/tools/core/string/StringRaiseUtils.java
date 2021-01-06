package com.iscas.common.tools.core.string;


import org.apache.commons.lang3.StringUtils;

/**
 *
 * 字符串扩展工具
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/22 20:02
 * @since jdk1.8
 */
public class StringRaiseUtils {
    private StringRaiseUtils() {
    }

    /**
     * 删除字符串中所有包含的字符串
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param str 原字符串
     * @param delStr 待删除的字符串
     * @throws
     * @return java.lang.String
     */
    public static String deleteAllString(String str, String delStr) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            while (str.contains(delStr)) {
                str = sb.append(StringUtils.substringBefore(str, delStr)).append(StringUtils.substringAfter(str, delStr)).toString();
                sb = new StringBuilder();
            }
        }
        return str;
    }


    /**
     * 将字符串的下划线转为驼峰命名
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param str 原字符串
     * @throws
     * @return java.lang.String
     */
    public static String convertToHump(String str) {
        String result = null;
        if (str == null || "".equalsIgnoreCase(str)) {
            result = str;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean flag = false;

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (i != 0 && '_' == ch) {
                    flag = true;
                } else {
                    if (flag == true) {
                        ch = Character.toUpperCase(ch);
                    }
                    flag = false;
                }
                if (ch != '_') {
                    sb.append(ch);
                }
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 将驼峰转为下划线
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param str 字符串
     * @throws
     * @return java.lang.String
     */
    public static String convertToUnderline(String str) {
        String result = null;
        if (str == null || "".equalsIgnoreCase(str)) {
            result = str;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (Character.isUpperCase(ch)) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(ch));
            }
            result = sb.toString();
        }
        return result;
    }
}
