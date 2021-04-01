package com.iscas.base.biz.config.elasticjob;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/1 13:36
 * @since jdk1.8
 */
@ConditionalOnElasticJobWithDatasource
public class ElasticDataSourceConfig {
    @Bean("elasticDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql1")
    public DataSource dataSource(){
        return new BasicDataSource();
    }
}
