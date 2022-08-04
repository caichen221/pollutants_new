package com.iscas.datasong.connector.conf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/29 16:22
 * @since jdk1.8
 */
public class ConnectionUrl {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 3306;

    private static String regex = "jdbc:datasong://.+:([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])/.+";
    private static Pattern compile = Pattern.compile(regex);

    public static boolean acceptsUrl(String connString) {
        //jdbc:datasong://192.168.100.88:3306/quick-frame-samples
        Matcher matcher = compile.matcher(connString);
        return matcher.matches();
    }
}
