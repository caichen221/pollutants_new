package com.iscas.biz.mp.aop.enable;

import com.iscas.biz.mp.config.shardingjdbc.ShardingJdbcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/27 9:42
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value={ShardingJdbcConfiguration.class})
public @interface EnableShardingJdbc {
}
