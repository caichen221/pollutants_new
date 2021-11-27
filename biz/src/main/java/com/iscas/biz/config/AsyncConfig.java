package com.iscas.biz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/26 10:41
 * @since jdk1.8
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer, BizConstant {
    private static int ASYNC_KEEPALIVE_SECONDS = 60;

    private static int  ASYNC_QUEUE_CAPACITY = 20000;

    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setQueueCapacity(20000);
        executor.setKeepAliveSeconds(ASYNC_KEEPALIVE_SECONDS);
        executor.setThreadNamePrefix(ASYNC_EXECUTOR_NAME_PREFIX);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


}
