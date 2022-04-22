package com.iscas.biz.mp.aop.db;

import com.iscas.biz.mp.config.db.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/8/31 13:23
 * @since jdk1.8
 */
@SuppressWarnings("unused")
@Aspect
@Component
@Slf4j
public class DsAspect {

    @Around(value = "@annotation(ds)")
    public Object around(final ProceedingJoinPoint joinPoint, DS ds) throws Throwable {
        try {
            DbContextHolder.setDbType(ds.value());
            log.debug("正在访问{}数据源...", ds.value());
            return joinPoint.proceed();
        } finally {
            DbContextHolder.clearDbType();
        }
    }
}
