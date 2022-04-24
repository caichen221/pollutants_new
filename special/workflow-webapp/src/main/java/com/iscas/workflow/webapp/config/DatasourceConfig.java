package com.iscas.workflow.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/1/27 13:49
 * @since jdk1.8
 */
@Configuration
@EnableConfigurationProperties(DatasourceProps.class)
public class DatasourceConfig {
    @Autowired
    private DatasourceProps datasourceProps;

    @Bean(name = "camundaBpmDataSource")
    public DataSource camundaBpmDataSource() {
        return datasourceProps.initializeDataSourceBuilder().build();
    }
}
