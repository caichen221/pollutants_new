package com.iscas.base.biz.aop.enable;

import com.iscas.base.biz.config.elasticjob.ElasticJobConfig;
import com.iscas.base.biz.config.socketio.SocketioConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>elasticJob服务器开关</>
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/26 10:14
 * @since jdk1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ElasticJobConfig.class)
public @interface EnableElasticJob {
}
