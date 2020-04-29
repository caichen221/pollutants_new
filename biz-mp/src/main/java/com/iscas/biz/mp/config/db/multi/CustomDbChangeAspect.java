package com.iscas.biz.mp.config.db.multi;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 15:04
 * @since jdk1.8
 */
//如果配置文件内spring.datasource.multi 不为true不加载
@ConditionalOnProperty(name = "spring.datasource.multi", havingValue = "true", matchIfMissing = false)
@Component
@Aspect
@Order(-100)
@Slf4j
public class CustomDbChangeAspect {
    @Pointcut("execution(* com.iscas.biz.mp.test.service.mysql1.*.*(..))")
//    @Pointcut("execution(* com.iscas.biz.service.t1.*.*(..))")
    private void mysqlAspect() {
    }

    @Pointcut("execution(* com.iscas.biz.mp.test.service.mysql2.*.*(..))")
//@Pointcut("execution(* com.iscas.biz.service.t2.*.*(..))")
    private void sqliteAspect() {
    }

    @Before( "mysqlAspect()" )
    public void db1() {
        log.info("正在访问mysql1数据源...");
        DbContextHolder.setDbType(DbTypeEnum.db1);
    }

    @Before("sqliteAspect()" )
    public void db2 () {
        log.info("正在访问mysql2数据源...");
        DbContextHolder.setDbType(DbTypeEnum.db2);
    }

}
