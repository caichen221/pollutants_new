package com.iscas.biz.jpa.config.db.multi;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 多数据源配置例子，如果想使用多数据源，请把{@link com.iscas.biz.jpa.config.db.single.DruidConfig}
 *  *  中的@Configuration注解注释掉，请把{@link com.iscas.biz.jpa.config.db.multi.JpaPrimaryConfig} 和{@link com.iscas.biz.jpa.config.db.multi.JpaSecondConfig}
 *  *  中的注解注释打开
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/14 8:51
 * @since jdk1.8
 */


//@Configuration
@Slf4j
public class DruidConfig {
    //Mysql1
    @Value("${spring.datasource.druid.mysql1.url}")
    private String dbUrl;
    @Value("${spring.datasource.druid.mysql1.username}")
    private String username;
    @Value("${spring.datasource.druid.mysql1.password}")
    private String password;
    @Value("${spring.datasource.druid.mysql1.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.druid.mysql1.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.druid.mysql1.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.druid.mysql1.max-active}")
    private int maxActive;
    @Value("${spring.datasource.druid.mysql1.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.druid.mysql1.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.mysql1.min-evictable-idle-time-millis:60000}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.mysql1.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.druid.mysql1.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.druid.mysql1.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.druid.mysql1.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.druid.mysql1.pool-prepared-statements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.druid.mysql1.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.druid.mysql1.filters:stat,wall,logback}")
    private String filters;
    @Value("${spring.datasource.druid.mysql1.filter.stat.log-slow-sql:true}")
    private boolean logslowSql;
    @Value("${spring.datasource.druid.mysql1.filter.stat.merge-sql:true}")
    private boolean mergeSql;
    @Value("${spring.datasource.druid.mysql1.filter.stat.slow-sql-millis:200}")
    private long slowSqlMill;

    //mysql2
    @Value("${spring.datasource.druid.mysql2.url}")
    private String dbUrl2;
    @Value("${spring.datasource.druid.mysql2.username}")
    private String username2;
    @Value("${spring.datasource.druid.mysql2.password}")
    private String password2;
    @Value("${spring.datasource.druid.mysql2.driver-class-name}")
    private String driverClassName2;
    @Value("${spring.datasource.druid.mysql2.initial-size}")
    private int initialSize2;
    @Value("${spring.datasource.druid.mysql2.min-idle}")
    private int minIdle2;
    @Value("${spring.datasource.druid.mysql2.max-active}")
    private int maxActive2;
    @Value("${spring.datasource.druid.mysql2.max-wait}")
    private int maxWait2;
    @Value("${spring.datasource.druid.mysql2.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis2;
    @Value("${spring.datasource.druid.mysql2.min-evictable-idle-time-millis:60000}")
    private int minEvictableIdleTimeMillis2;
    @Value("${spring.datasource.druid.mysql2.validation-query}")
    private String validationQuery2;
    @Value("${spring.datasource.druid.mysql2.test-while-idle}")
    private boolean testWhileIdle2;
    @Value("${spring.datasource.druid.mysql2.test-on-borrow}")
    private boolean testOnBorrow2;
    @Value("${spring.datasource.druid.mysql2.test-on-return}")
    private boolean testOnReturn2;
    @Value("${spring.datasource.druid.mysql2.pool-prepared-statements}")
    private boolean poolPreparedStatements2;
    @Value("${spring.datasource.druid.mysql2.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize2;
    @Value("${spring.datasource.druid.mysql2.filters:stat,wall,logback}")
    private String filters2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.log-slow-sql:true}")
    private boolean logslowSql2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.merge-sql:true}")
    private boolean mergeSql2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.slow-sql-millis:200}")
    private long slowSqlMill2;


    @Bean(name="primaryDataSource")
    @Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.mysql1")
    public DataSource primaryDataSource() throws Exception{
        log.info("------------注册DruidDatasource-----------");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        //        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        log.info("------------注册Druid过滤器-----------");
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter: "+ e);
        }
        datasource.setProxyFilters(Arrays.asList(statFilter(),logFilter()));
        log.info("------------注册Druid过滤器结束-----------");
        log.info("------------注册DruidDatasource结束-----------");
        return datasource;
    }

    @Bean(name="secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.mysql2")
    public DataSource secondDataSource() throws Exception{
        log.info("------------注册DruidDatasource-----------");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        //        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        log.info("------------注册Druid过滤器-----------");
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter: "+ e);
        }
        datasource.setProxyFilters(Arrays.asList(statFilter(),logFilter()));
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
}
