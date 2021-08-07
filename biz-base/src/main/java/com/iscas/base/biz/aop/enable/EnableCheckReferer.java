package com.iscas.base.biz.aop.enable;

import com.iscas.base.biz.config.referer.RefererFilterConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 校验referer的开关
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/7 13:46
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RefererFilterConfig.class)
public @interface EnableCheckReferer {
}
