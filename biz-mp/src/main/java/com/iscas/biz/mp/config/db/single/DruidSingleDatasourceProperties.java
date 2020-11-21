package com.iscas.biz.mp.config.db.single;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/18 20:40
 * @since jdk1.8
 */
//多数据源请注释掉注解
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource.druid")
@ConditionalOnProperty(name = "spring.datasource.single", havingValue = "true", matchIfMissing = false)
@Primary
public class DruidSingleDatasourceProperties extends DataSourceProperties {

}
