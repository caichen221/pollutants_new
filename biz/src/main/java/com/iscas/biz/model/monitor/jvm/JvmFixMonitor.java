package com.iscas.biz.model.monitor.jvm;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/2 17:20
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
@ToString
public class JvmFixMonitor {
    /**
     * 虚拟机名称
     */
    private String name;

    /**
     * 虚拟机版本
     */
    private String version;

    /**
     * 厂商
     */
    private String vendor;

    /**
     * 启动时间
     */
    private String startTime;
}
