package com.iscas.base.biz.config.ratelimiter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 限流属性
 * */
@ConfigurationProperties(prefix = "rate.limiter")
@Data
//@Component
public class RateLimiterProps {
    private double permitsPerSecond = 20; //每秒产生令牌数
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration maxWait = Duration.ofMillis(500); //获取令牌最大等待时间毫秒数
    private List<String> staticUrl; //静态资源

}
