package com.iscas.base.biz.config.norepeat.submit;

import org.springframework.context.annotation.Bean;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:58
 * @since jdk1.8
 */
public class NoRepeatSubmitRedisConfig {
    @Bean
    public NoRepeatSubmitBean bean() {
        return new NoRepeatSubmitBean(NoRepeatSubmitLockType.REDIS);
    }
}
