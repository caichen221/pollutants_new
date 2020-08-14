package com.iscas.biz.mp.config.db.multi;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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


    @Bean(name = "mysql1")
    @ConditionalOnProperty(name = "spring.datasource.druid.mysql1.name", havingValue = "mysql1",
            matchIfMissing = true)
//    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql1")
    public DataSource db1() {
        DruidDataSource datasource = (DruidDataSource) datasource1Properties.initializeDataSourceBuilder().build();

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
