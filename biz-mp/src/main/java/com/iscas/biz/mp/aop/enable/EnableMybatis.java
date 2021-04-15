package com.iscas.biz.mp.aop.enable;

import com.iscas.biz.mp.config.db.*;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 9:42
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value={DruidConfiguration.class, MultiDatasourceAspectJExpressionPointcutAdvisor.class,
        MybatisPlusConfig.class})
public @interface EnableMybatis {
}
