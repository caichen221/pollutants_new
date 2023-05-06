package com.iscas.biz;

import cn.hutool.core.io.IoUtil;
import com.iscas.base.biz.aop.enable.EnableWebsocketStomp;
import com.iscas.base.biz.config.stomp.WsPushType;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.tools.core.runtime.RuntimeUtils;
import com.iscas.templet.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.amqp.RabbitMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 启动类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2018/10/10 17:45
 * @since jdk1.8
 */
@SuppressWarnings("resource")
@Configuration
//暂时抛除rabbitmq的自动注册，如果使用代理websocket推送需要去掉
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class, RabbitMetricsAutoConfiguration.class,
        RabbitMetricsAutoConfiguration.class, DataSourceAutoConfiguration.class, /*MybatisAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,*/ DataSourceHealthContributorAutoConfiguration.class, XADataSourceAutoConfiguration.class})
@ServletComponentScan //自动扫描servletBean
@ComponentScan(basePackages = {"com.iscas"}
        , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.iscas.biz.test.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.iscas.biz.mp.test.*")
        ,
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.iscas.base.biz.test.*")
}
)
@EnableCaching //开启缓存
@EnableTransactionManagement //开启事务支持
@EnableWebsocketStomp(pushType = WsPushType.SIMPLE) //开启websocketstomp支持

// biz-mp-spring-boot-starter使用的几个注解
//@EnableDruidMonitor //开启Druid监控（未使用biz-mp或biz-jpa模块时无法使用）
//@EnableMybatis //mybatis开关,不启用Mybatis时最好把@EnableAuth也注释，不然认证授权会报错
////@EnableAtomikos //开启Atomikos分布式事务（有些数据库需要给权限）
////@EnableShardingJdbc //是否开启分库分表
//@EnableQuartz // 允许quartz
@Slf4j
public class BizApp extends SpringBootServletInitializer {
    @Value("${server.port}")
    private String serverPort;

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch("appStart");
        stopWatch.start();
        SpringApplication springApplication = new SpringApplication(BizApp.class);
        springApplication.run(args);
        stopWatch.stop();
        log.info("服务已启动，启动耗时：{}秒,进程号:[{}],端口号:[{}]", stopWatch.getTotalTimeSeconds(), RuntimeUtils.getCurrentPid(),
                SpringUtils.getBean(Environment.class).getProperty("server.port"));
        try (OutputStream os = new FileOutputStream("newframe.pid")) {
            IoUtil.writeUtf8(os, true, RuntimeUtils.getCurrentPid());
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 重写configure
     *
     * @param builder builder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        StopWatch stopWatch = new StopWatch("appStart");
        stopWatch.start();
        SpringApplicationBuilder sources = builder.sources(BizApp.class);
        stopWatch.stop();
        log.info("服务已启动，启动耗时：{}秒,进程号:[{}],端口号:[{}]", stopWatch.getTotalTimeSeconds(), RuntimeUtils.getCurrentPid(),
                serverPort);
        try (OutputStream os = new FileOutputStream("newframe.pid")) {
            IoUtil.writeUtf8(os, true, RuntimeUtils.getCurrentPid());
        } catch (IOException e) {
            throw Exceptions.runtimeException(e);
        }
        return sources;
    }

}
