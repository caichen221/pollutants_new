//package com.iscas.biz.mp.config.db.single;
//
//
//import com.alibaba.druid.filter.logging.Slf4jLogFilter;
//import com.alibaba.druid.filter.stat.StatFilter;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.type.JdbcType;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Arrays;
//
///**
// * 单数据源配置，如果使用单数据源，请把{@link com.iscas.biz.mp.config.db.multi.DruidConfiguration}
// *  中的@Configuration注解注释掉，请把{@link com.iscas.biz.mp.config.db.multi.CustomDbChangeAspect}
// *  中的注解注释掉
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2018/8/31 11:01
// * @since jdk1.8
// */
//
//@Slf4j
//@Configuration //如果使用多数据源请把这行注释掉
//public class DruidConfigurationbackup {
//    @Value("${spring.datasource.druid.url}")
//    private String dbUrl;
//    @Value("${spring.datasource.druid.username}")
//    private String username;
//    @Value("${spring.datasource.druid.password}")
//    private String password;
//    @Value("${spring.datasource.druid.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.druid.initial-size}")
//    private int initialSize;
//    @Value("${spring.datasource.druid.min-idle}")
//    private int minIdle;
//    @Value("${spring.datasource.druid.max-active}")
//    private int maxActive;
//    @Value("${spring.datasource.druid.max-wait}")
//    private int maxWait;
//    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
//    private int timeBetweenEvictionRunsMillis;
//    @Value("${spring.datasource.druid.min-evictable-idle-time-millis:60000}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${spring.datasource.druid.validation-query}")
//    private String validationQuery;
//    @Value("${spring.datasource.druid.test-while-idle}")
//    private boolean testWhileIdle;
//    @Value("${spring.datasource.druid.test-on-borrow}")
//    private boolean testOnBorrow;
//    @Value("${spring.datasource.druid.test-on-return}")
//    private boolean testOnReturn;
//    @Value("${spring.datasource.druid.pool-prepared-statements}")
//    private boolean poolPreparedStatements;
//    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${spring.datasource.druid.filters:stat,wall,logback}")
//    private String filters;
//
//    @Value("${spring.datasource.druid.filter.stat.log-slow-sql:true}")
//    private boolean logslowSql;
//    @Value("${spring.datasource.druid.filter.stat.merge-sql:true}")
//    private boolean mergeSql;
//    @Value("${spring.datasource.druid.filter.stat.slow-sql-millis:200}")
//    private long slowSqlMill;
////    @Value("${spring.datasource.connectionProperties}")
////    private String connectionProperties;
////    @Value("${spring.datasource.useGlobalDataSourceStat}")
////    private boolean useGlobalDataSourceStat;
//
//    @Bean     //声明其为Bean实例
//    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
//    public DataSource dataSource(){
//        log.info("------------注册DruidDatasource-----------");
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(this.dbUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//
//        //configuration
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        datasource.setValidationQuery(validationQuery);
//        datasource.setTestWhileIdle(testWhileIdle);
//        datasource.setTestOnBorrow(testOnBorrow);
//        datasource.setTestOnReturn(testOnReturn);
//        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//
//        //        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
//        log.info("------------注册Druid过滤器-----------");
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter: "+ e);
//        }
//        datasource.setProxyFilters(Arrays.asList(statFilter(),logFilter()));
//        log.info("------------注册Druid过滤器结束-----------");
//        log.info("------------注册DruidDatasource结束-----------");
//        return datasource;
//    }
//
////    @Bean
////    @Primary
////    public SqlSessionFactory sqlSessionFactory() throws Exception {
////        log.info("----------------注册SqlSessionFactory开始-----------------");
////        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
////        sqlSessionFactory.setDataSource(dataSource());
//////        sqlSessionFactory.setDataSource(multipleDataSource);
////        //sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*Mapper.xml"));
////
////        MybatisConfiguration configuration = new MybatisConfiguration();
////        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
////        configuration.setJdbcTypeForNull(JdbcType.NULL);
////        configuration.setMapUnderscoreToCamelCase(true);
////        configuration.setCacheEnabled(false);
////        sqlSessionFactory.setConfiguration(configuration);
//////        sqlSessionFactory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
//////                paginationInterceptor() //添加分页功能
//////        });
//////        sqlSessionFactory.setGlobalConfig(globalConfiguration());
////        log.info("----------------注册SqlSessionFactory结束-----------------");
////        return sqlSessionFactory.getObject();
////    }
//
//
//    @Bean
//    @Primary
//    public StatFilter statFilter(){
//        StatFilter statFilter = new StatFilter();
//        statFilter.setSlowSqlMillis(slowSqlMill);
//        statFilter.setLogSlowSql(logslowSql);
//        statFilter.setMergeSql(mergeSql);
//        return statFilter;
//    }
//
//    @Bean
//    public Slf4jLogFilter logFilter(){
//        Slf4jLogFilter filter = new Slf4jLogFilter();
////        filter.setResultSetLogEnabled(false);
////        filter.setConnectionLogEnabled(false);
////        filter.setStatementParameterClearLogEnable(false);
////        filter.setStatementCreateAfterLogEnabled(false);
////        filter.setStatementCloseAfterLogEnabled(false);
////        filter.setStatementParameterSetLogEnabled(false);
////        filter.setStatementPrepareAfterLogEnabled(false);
//        return  filter;
//    }
//
//}
