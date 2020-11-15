package com.iscas.biz.mp.config.db.multi;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置例子
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 13:48
 * @since jdk1.8
 */
@Slf4j
@ConditionalOnProperty(name = "spring.datasource.multi", havingValue = "true", matchIfMissing = false)
@Configuration
public class DruidConfiguration {

    //mysql1
    @Value("${spring.datasource.druid.mysql1.filters:stat,wall,logback}")
    private String filters;
    @Value("${spring.datasource.druid.mysql1.filter.stat.log-slow-sql:true}")
    private boolean logslowSql;
    @Value("${spring.datasource.druid.mysql1.filter.stat.merge-sql:true}")
    private boolean mergeSql;
    @Value("${spring.datasource.druid.mysql1.filter.stat.slow-sql-millis:200}")
    private long slowSqlMill;

    //mysql2

    @Value("${spring.datasource.druid.mysql2.filters:stat,wall,logback}")
    private String filters2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.log-slow-sql:true}")
    private boolean logslowSql2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.merge-sql:true}")
    private boolean mergeSql2;
    @Value("${spring.datasource.druid.mysql2.filter.stat.slow-sql-millis:200}")
    private long slowSqlMill2;

    @Value("${mybatis-plus.global-config.db-config.id-type}")
    private String idType;
    @Value("${mybatis-plus.type-enums-package}")
    private String enumPackages;

    @Autowired
    private DruidMultiDatasource1Properties datasource1Properties;
    @Autowired
    private DruidMultiDatasource2Properties datasource2Properties;

    //Mysql1
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

    //mysql2
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


    @Bean(name = "mysql1")
    @ConditionalOnProperty(name = "spring.datasource.druid.mysql1.name", havingValue = "mysql1",
            matchIfMissing = true)
//    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql1")
    public DataSource db1() {
        DruidDataSource datasource = (DruidDataSource) datasource1Properties.initializeDataSourceBuilder().build();
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
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter: " + e);
//        }
        datasource.setProxyFilters(Arrays.asList(statFilter(), logFilter(), wallFilter()));
//        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }


    @Bean(name = "mysql2")
    @ConditionalOnProperty(name = "spring.datasource.druid.mysql2.name", havingValue = "mysql2",
            matchIfMissing = true)
//    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql2")
    public DataSource db2() {
        DruidDataSource datasource = (DruidDataSource) datasource2Properties.initializeDataSourceBuilder().build();

        //configuration
        datasource.setInitialSize(initialSize2);
        datasource.setMinIdle(minIdle2);
        datasource.setMaxActive(maxActive2);
        datasource.setMaxWait(maxWait2);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis2);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis2);
        datasource.setValidationQuery(validationQuery2);
        datasource.setTestWhileIdle(testWhileIdle2);
        datasource.setTestOnBorrow(testOnBorrow2);
        datasource.setTestOnReturn(testOnReturn2);
        datasource.setPoolPreparedStatements(poolPreparedStatements2);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize2);
//        try {
//            datasource.setFilters(filters2);
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter: " + e);
//        }
        datasource.setProxyFilters(Arrays.asList(statFilter(), logFilter(), wallFilter()));
//        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("mysql1") DataSource db1,
                                         @Qualifier("mysql2") DataSource db2) {

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2 << 2);
        targetDataSources.put(DbTypeEnum.db1.getValue(), db1);
        targetDataSources.put(DbTypeEnum.db2.getValue(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(multipleDataSource(db1(), db2()));
//        sqlSessionFactory.setDataSource(multipleDataSource);
        //sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*Mapper.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        factory.setConfiguration(configuration);
//        factory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
//                paginationInterceptor() //添加分页功能
//        });
        PaginationInterceptor paginationInterceptor = paginationInterceptor();
        factory.setPlugins(paginationInterceptor);
        if (StringUtils.hasLength(enumPackages)) {
            factory.setTypeEnumsPackage(enumPackages);
        }
        factory.setGlobalConfig(globalConfiguration());
        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }


    private GlobalConfig globalConfiguration() {
        GlobalConfig conf = GlobalConfigUtils.defaults();
        conf.getDbConfig().setIdType(IdType.valueOf(idType));
        return conf;
    }


    @Bean
    @Primary
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(slowSqlMill);
        statFilter.setLogSlowSql(logslowSql);
        statFilter.setMergeSql(mergeSql);
        return statFilter;
    }

    @Bean
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
//        filter.setResultSetLogEnabled(false);
//        filter.setConnectionLogEnabled(false);
//        filter.setStatementParameterClearLogEnable(false);
//        filter.setStatementCreateAfterLogEnabled(false);
//        filter.setStatementCloseAfterLogEnabled(false);
//        filter.setStatementParameterSetLogEnabled(false);
//        filter.setStatementPrepareAfterLogEnabled(false);
        return filter;
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
}
