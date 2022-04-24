package com.iscas.biz.config.log;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lirenshen
 * @version 1.0
 * @date 2021/2/20 17:24
 * @since jdk1.8
 */
public class LogManager {
    private static final int OPERATE_DELAY_TIME = 10;

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private LogManager() {
    }

    public static void executeLog(Runnable task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
