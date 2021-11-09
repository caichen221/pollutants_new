package com.iscas.common.etcd.tools.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/28 9:02
 * @since jdk1.8
 */
public class ConfigUtils {
    private ConfigUtils() {}

    public static InputStream getConfigIs(String path) throws FileNotFoundException {
        if (path.startsWith("classpath:")) {
            return ConfigUtils.class.getClassLoader().getResourceAsStream(path.substring(10));
        } else {
            return new FileInputStream(path);
        }
    }
}
