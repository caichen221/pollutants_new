package com.iscas.biz.model.monitor.jvm;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/2 16:56
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
@ToString
public class JvmMonitor {


    /**
     * 运行时间
     */
    private String runningTime;

}
