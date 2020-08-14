package com.iscas.biz.mp.config.db.single;


import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 单数据源配置
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/8/31 11:01
 * @since jdk1.8
 */

@Slf4j
@ConditionalOnProperty(name = "spring.datasource.single", havingValue = "true", matchIfMissing = false)
@Configuration
public class DruidConfiguration {
    @Autowired
    private DruidSingleDatasourceProperties druidSingleDatasourceProperties;

    @Value("${spring.datasource.druid.filters:stat,wall,logback}")
    private String filters;

    @Value("${spring.datasource.druid.filter.stat.log-slow-sql:true}")
    private boolean logslowSql;
    @Value("${spring.datasource.druid.filter.stat.merge-sql:true}")
    private boolean mergeSql;

    @Value("${spring.datasource.druid.filter.stat.slow-sql-millis:200}")
    private long slowSqlMill;
//    @Value("${spring.datasource.connectionProperties}")
//    private String connectionProperties;
//    @Value("${spring.datasource.useGlobalDataSourceStat}")
//    private boolean useGlobalDataSourceStat;

    @Bean     //声明其为Bean实例
    @ConditionalOnProperty(name = "spring.datasource.druid.type", havingValue = "com.alibaba.druid.pool.DruidDataSource",
            matchIfMissing = true)
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){
        log.info("------------注册DruidDatasource-----------");

        DruidDataSource datasource = (DruidDataSource) druidSingleDatasourceProperties.initializeDataSourceBuilder().build();

        //        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        log.info("------------注册Druid过滤器-----------");
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter: "+ e);
//        }
        datasource.setProxyFilters(Arrays.asList(statFilter(),logFilter(), wallFilter()));
        log.info("------------注册Druid过滤器结束-----------");
        log.info("------------注册DruidDatasource结束-----------");
        return datasource;
    }

    @Bean
    @Primary
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(slowSqlMill);
        statFilter.setLogSlowSql(logslowSql);
        statFilter.setMergeSql(mergeSql);
        return statFilter;
    }

    @Bean
    public Slf4jLogFilter logFilter(){
        Slf4jLogFilter filter = new Slf4jLogFilter();
//        filter.setResultSetLogEnabled(false);
//        filter.setConnectionLogEnabled(false);
//        filter.setStatementParameterClearLogEnable(false);
//        filter.setStatementCreateAfterLogEnabled(false);
//        filter.setStatementCloseAfterLogEnabled(false);
//        filter.setStatementParameterSetLogEnabled(false);
//        filter.setStatementPrepareAfterLogEnabled(false);
        return  filter;
    }

    @Bean

    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;

    }

    @Bean

    public WallConfig wallConfig() {
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);//允许一次执行多条语句
        config.setNoneBaseStatementAllow(true);//允许非基本语句的其他语句
        return config;

    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
