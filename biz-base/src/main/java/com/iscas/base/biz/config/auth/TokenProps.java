package com.iscas.base.biz.config.auth;

import com.iscas.base.biz.util.JWTUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;

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
//@Component
@ConstructorBinding
@ConfigurationProperties(prefix = "token")
public class TokenProps {

    /**token过期时间(分钟)*/
    private Duration expire = Duration.ofMinutes(14440);

    /** token保存在cookie的时间(毫秒)*/
    private int cookieExpire = -1;

    /**是否将token存入cookie*/
    private boolean cookieStore;

    /**
     * token生成方式
     * */
    private JWTUtils.AlgorithmType creatorMode;

    public TokenProps(@DurationUnit(ChronoUnit.MINUTES) @DefaultValue("1440m") Duration expire,
                      @DefaultValue("-1") int cookieExpire, @DefaultValue("true") boolean cookieStore,
                      @DefaultValue("hmac256") String creatorMode) {
        this.expire = expire;
        this.cookieExpire = cookieExpire;
        this.cookieStore = cookieStore;
        this.creatorMode = JWTUtils.AlgorithmType.getEnum(creatorMode);
    }

}