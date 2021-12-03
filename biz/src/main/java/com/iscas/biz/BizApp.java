package com.iscas.biz;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import com.iscas.base.biz.aop.enable.*;
import com.iscas.base.biz.config.norepeat.submit.NoRepeatSubmitLockType;
import com.iscas.base.biz.config.stomp.WsPushType;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.biz.mp.aop.enable.EnableAtomikos;
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
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger.web.ApiResourceController;

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
@EnableDatasongClientPlus //是否开启Datasongclient客户端
@EnableSocketio //是否开启Socketio的支持
//@EnableElasticJob(withDatasource = false) //更新为elastic-job3.0后暂不支持日志记录到数据库
@EnableMybatis //mybatis开关,不启用Mybatis时最好把@EnableAuth也注释，不然认证授权会报错
@EnableRetry(proxyTargetClass = true) //是否允许方法重试功能
@EnableAtomikos //开启Atomikos分布式事务（有些数据库需要给权限）
@EnableShedLock //shedlock开关，spring定时任务锁（暂时只能应用到spring的@Scheduled定时任务上）
//@EnableShardingJdbc //是否开启分库分表
@EnableSpringBootAdminClient //是否开启springboot-admin客户端，如果不使用可以关闭，防止一直连接admin服务
//@EnableCustomCasClient //是否开启自定义的Cas客户端
//@EnableCheckReferer //是否校验referer，需配合配置文件内的域名白名单
@RetrofitScan("com.iscas.biz.test.retrofit") //扫描retrofit的包
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
