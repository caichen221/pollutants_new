package com.iscas.common.tools.core.valid;

import com.iscas.common.tools.constant.StrConstantEnum;

/**
 * 校验IP地址工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/9/17 13:50
 * @since jdk1.8
 */
public class IpValidUtils {
    private static String PERIOD = StrConstantEnum.PERIOD.getValue();
    private static String COLON = StrConstantEnum.COLON.getValue();
    private static String COLON2 = StrConstantEnum.COLON.getValue().concat(StrConstantEnum.COLON.getValue());
    private IpValidUtils() {}

    /**
     * 判断所有的IP地址是IPV4 还是IPV6
     * @param ip ip地址字符串
     * @return
     */
    public static String validIp4Or6(String ip) {

        if (!ip.contains(PERIOD) && !ip.contains(COLON)) {
            return "Neither";
        }
        //如果是IPV4
        if (ip.contains(PERIOD)) {
            if (ip.endsWith(PERIOD)) {
                return "Neither";
            }
            String[] arr = ip.split("\\.");
            if (arr.length != 4) {
                return "Neither";
            }

            for (int i = 0; i < 4; i++) {
                if (arr[i].length() == 0 || arr[i].length() > 3) {
                    return "Neither";
                }
                for (int j = 0; j < arr[i].length(); j++) {
                    if (arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') {
                        continue;
                    }
                    return "Neither";
                }
                if (Integer.valueOf(arr[i]) > 255 || (arr[i].length() >= 2 && String.valueOf(arr[i]).startsWith("0"))) {
                    return "Neither";
                }
            }
            return "IPv4";
        }//如果是IPV4

        //如果是IPV6
        if (ip.contains(COLON)) {
            if (ip.endsWith(COLON) && !ip.endsWith(COLON2)) {
                return "Neither";
            }
            //如果包含多个“::”，一个IPv6地址中只能出现一个“::”
            if (ip.indexOf(COLON2) != -1 && ip.indexOf(COLON2, ip.indexOf(COLON2) + 2) != -1) {
                return "Neither";
            }

            //如果含有一个“::”
            if (ip.contains(COLON2)) {
                String[] arr = ip.split(COLON);
                if (arr.length > 7 || arr.length < 1) {//"1::"是最短的字符串
                    return "Neither";
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals(StrConstantEnum.EMPTY.getValue())) {
                        continue;
                    }
                    if (arr[i].length() > 4) {
                        return "Neither";
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return "Neither";
                    }
                }
                return "IPv6";
            }

            //如果不含有“::”
            if (!ip.contains(COLON2)) {
                String[] arr = ip.split(COLON2);
                if (arr.length != 8) {
                    return "Neither";
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].length() > 4) {
                        return "Neither";
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return "Neither";
                    }
                }
                return "IPv6";
            }
        }//如果是IPV6
        return "Neither";
    }
}
