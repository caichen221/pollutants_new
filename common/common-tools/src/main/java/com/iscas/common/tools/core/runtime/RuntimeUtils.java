package com.iscas.common.tools.core.runtime;

import java.lang.management.ManagementFactory;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/31 13:21
 * @since jdk1.8
 */
public class RuntimeUtils {
    private RuntimeUtils() {}

    /**
     * 获取当前的进程号
     * */
    public static Integer getCurrentPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String[] names = name.split("@");
        int pid = Integer.parseInt(names[0]);
        return pid;
    }
}