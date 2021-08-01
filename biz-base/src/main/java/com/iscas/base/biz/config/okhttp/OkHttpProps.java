package com.iscas.base.biz.config.okhttp;//package com.iscas.cmi.config.okhttp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * 自动配置属性
 **/
@ConfigurationProperties(prefix = "okhttp")
@Data
@Validated
public class OkHttpProps {
    @Min(2000)
    private int readTimeout = 10000; //读取超时时间毫秒
    @Min(2000)
    private int writeTimeout = 10000 ; //写数据超时时间毫秒
    @Min(2000)
    private int connectTimeout = 10000; //连接超时时间毫秒
    private int maxIdleConnection = 15 ; //最大空闲数目
    private long keepAliveDuration = 5 ; //keep alive保持时间 分钟


}
