package com.iscas.biz.mp.config.db.multi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/18 21:36
 * @since jdk1.8
 */
@ConditionalOnProperty(name = "spring.datasource.multi", havingValue = "true", matchIfMissing = false)
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource.druid.mysql2")
public class DruidMultiDatasource2Properties extends DataSourceProperties {
}
