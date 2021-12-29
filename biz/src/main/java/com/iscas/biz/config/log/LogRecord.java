package com.iscas.biz.config.log;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/02/21
 * @since jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogRecord {
    LogType type() default LogType.UNKONW;
    String desc();
    OperateType operateType();
}
