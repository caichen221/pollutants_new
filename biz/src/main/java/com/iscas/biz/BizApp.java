package com.iscas.biz;

import com.iscas.base.biz.aop.enable.*;
import com.iscas.base.biz.config.norepeat.submit.NoRepeatSubmitLockType;
import com.iscas.base.biz.config.stomp.WsPushType;
import com.iscas.biz.mp.aop.enable.EnableDruidMonitor;
import com.iscas.biz.mp.aop.enable.EnableMybatis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.amqp.RabbitMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/10/10 17:45
 * @since jdk1.8
 */
@Configuration
//暂时抛除rabbitmq的自动注册，如果使用代理websocket推送需要去掉
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class, RabbitMetricsAutoConfiguration.class,
        RabbitMetricsAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ServletComponentScan //自动扫描servletBean
@ComponentScan(basePackages = {"com.iscas"})
@EnableNoRepeatSubmit(lockType = NoRepeatSubmitLockType.JVM)  //是否开启防重复提交
@EnableCaching //开启缓存
@EnableTransactionManagement //开启事务支持
@EnableRateLimiter //开启自定义的限流支持
@EnableAuth //开启自定义的用户认证，权限校验
@EnableWebsocketStomp(pushType = WsPushType.SIMPLE) //开启websocketstomp支持
@EnableLog //允许日志记录
@EnableXssConfig //开启Xss过滤器
@EnableDruidMonitor //开启Druid监控（未使用biz-mp或biz-jpa模块时无法使用）
//@EnableSecurity //是否开启rsa接口请求以及返回值的加解密，可在非https下使用，需要在接口使用注解
@EnableHealthCheck //开启健康检测 readiness liveness
@EnableDatasongClientPlus //是否开启Datasongclient客户端，如果关闭请把biz-base模块下com.iscas.base.biz.test.datasongplus下所有内容注释或删掉
@EnableSocketio //是否开启Socketio的支持
//@EnableElasticJob(withDatasource = true)
@EnableMybatis //mybatis开关,不启用Mybatis时最好把@EnableAuth也注释，不然认证授权会报错
@Slf4j
public class BizApp extends SpringBootServletInitializer {
    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(BizApp.class);
//        springApplication.addListeners(new MyApplicationBeforeStartListener(), new MyApplicationStartedListener());
        springApplication.run(args);
        log.info("==========服务已启动=================");
    }

    /**
     *重写configure
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        builder.listeners(new MyApplicationBeforeStartListener(), new MyApplicationStartedListener());
        SpringApplicationBuilder sources = builder.sources(BizApp.class);
        log.info("==========服务已启动=================");
        return sources;
    }

}
