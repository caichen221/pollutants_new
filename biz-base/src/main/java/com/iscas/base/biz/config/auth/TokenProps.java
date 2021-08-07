package com.iscas.base.biz.config.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * token配置表
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/17 8:31
 * @since jdk1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProps {

    /**token过期时间(分钟)*/
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration expire = Duration.ofMinutes(14440);
    /** token保存在cookie的时间(毫秒)*/
    private int cookieExpire = -1;

    /**是否将token存入cookie*/
    private boolean cookieStore;
}
