package com.iscas.biz.mp.config.db;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.aop.enable.EnableAtomikos;
import com.iscas.biz.mp.aop.enable.EnableMybatis;
import com.iscas.biz.mp.aop.enable.EnableShardingJdbc;
import com.iscas.biz.mp.interfaces.IShardingJdbcHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.SystemException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 多数据源配置例子
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 13:48
 * @since jdk1.8
 */
@Slf4j
//@Configuration
@ConditionalOnMybatis
public class DruidConfiguration implements EnvironmentAware {

    @Value("${mybatis-plus.global-config.db-config.id-type}")
    private String idType;
    @Value("${mybatis-plus.type-enums-package}")
    private String enumPackages;
    private final String basePath = "spring.datasource.druid.";
    private Environment environment;
    @Autowired
    private ApplicationContext context;

    @Bean(name="dynamicDatasource")
    public DataSource dynamicDataSource() throws SQLException {
        String db = environment.getProperty("spring.datasource.names");

        Map<Object, Object> targetDataSources = new LinkedHashMap<>(2 << 2);
        Map<String, Object> enableAtomikosMap = context.getBeansWithAnnotation(EnableAtomikos.class);
        if (db != null) {
            List<String> dbNames = Arrays.asList(db.split(","));
            if (CollectionUtil.isNotEmpty(dbNames)) {
                for (String dbName : dbNames) {
                    //初始化数据源
                    //update by zqw 20210520 添加Atomikos支持
                    DruidDataSource dataSource = initOneDatasource(dbName);
                    if (dataSource != null) {
                        if (MapUtil.isNotEmpty(enableAtomikosMap)) {
                            //如果开启了Atomikos
                            AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
                            atomikosDataSourceBean.setXaDataSource((XADataSource) dataSource);
                            //必须设置uniqueResourceName
                            atomikosDataSourceBean.setUniqueResourceName(dbName);
                            targetDataSources.put(dbName, atomikosDataSourceBean);
                        } else {
                            //如果没开启Atomikos
                            targetDataSources.put(dbName, dataSource);
                        }
                    }
                }
            }
        }

        //如果有shardingjdbc的配置，读取
        Map<String, Object> enableShardingJdbcMap = context.getBeansWithAnnotation(EnableShardingJdbc.class);
        if (CollectionUtil.isNotEmpty(enableShardingJdbcMap)) {
            IShardingJdbcHandler shardingJdbcHandler = context.getBean(IShardingJdbcHandler.class);
            if (shardingJdbcHandler != null) {
                targetDataSources.putAll(shardingJdbcHandler.initShardingDatasource());
            }
//            DataSource dataSource = getShardingDatasource();
//            targetDataSources.put("ds0_ds1", dataSource);
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        //设置数据源
        if (targetDataSources.size() > 0) {
            dynamicDataSource.setTargetDataSources(targetDataSources);
            dynamicDataSource.setDefaultTargetDataSource(targetDataSources.entrySet().iterator().next().getValue());
        } else {
            log.error("没有数据源，无法使用数据库!!!");
            return null;
        }
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }



    private DruidDataSource initOneDatasource(String dbName) throws SQLException {
        // update by zqw 20210520 将DruidDataSource修改为DruidXADataSource，为了Atomikos
        DruidDataSource datasource = null;
        Map<String, Object> enableAtomikosMap = context.getBeansWithAnnotation(EnableAtomikos.class);
        if (MapUtil.isNotEmpty(enableAtomikosMap)) {
            datasource = new DruidXADataSource();
        } else {
            datasource = new DruidDataSource();
        }
        String value;
        String path = basePath + dbName + ".";

        //将构建datasource的一部分把代码抽成静态方法，sharding-jdbc 调用一下 update by zqw 20210527
        datasource = doInitOneDatasource(path, dbName, datasource, environment);
        //Filters
        value = environment.getProperty(path + "filters");
        if (StringUtils.isNotBlank(value)) {
            List<Filter> filterList = handlerFilters(path + "filter.", value);
            if (filterList.size() > 0) {
                datasource.setProxyFilters(filterList);
            }
        }
        datasource.init();

        return datasource;
    }

    /**
     * 将构建datasource的一部分把代码抽成静态方法，sharding-jdbc
     * 调用一下 update by zqw 20210527
     * */
    public static DruidDataSource doInitOneDatasource(String path, String dbName, DruidDataSource datasource,
                                                      Environment environment) {
        String value;

        datasource.setName(dbName);
        //必选参数
        value = environment.getProperty(path + "username");
        if (StringUtils.isBlank(value)) {
            log.error("数据源username不能为空");
            return null;
        }
        datasource.setUsername(value);
        value = environment.getProperty(path + "password");
        if (StringUtils.isBlank(value)) {
            log.error("数据源password不能为空");
            return null;
        }
        datasource.setPassword(value);
        value = environment.getProperty(path + "url");
        if (StringUtils.isBlank(value)) {
            log.error("url");
            return null;
        }
        datasource.setUrl(value);
        value = environment.getProperty(path + "driver-class-name");
        if (StringUtils.isBlank(value)) {
            log.error("数据源driver-class-name不能为空");
            return null;
        }
        datasource.setDriverClassName(value);

        //可选参数
        value = environment.getProperty(path + "initial-size");
        if (StringUtils.isNotBlank(value)) {
            datasource.setInitialSize(Integer.parseInt(value));
        }
        value = environment.getProperty(path + "min-idle");
        if (StringUtils.isNotBlank(value)) {
            datasource.setMinIdle(Integer.parseInt(value));
        }
        value = environment.getProperty(path + "max-active");
        if (StringUtils.isNotBlank(value)) {
            datasource.setMaxActive(Integer.parseInt(value));
        }
        value = environment.getProperty(path + "max-wait");
        if (StringUtils.isNotBlank(value)) {
            datasource.setMaxWait(Long.parseLong(value));
        }
        value = environment.getProperty(path + "time-between-eviction-runs-millis");
        if (StringUtils.isNotBlank(value)) {
            datasource.setTimeBetweenEvictionRunsMillis(Long.parseLong(value));
        }
        value = environment.getProperty(path + "validation-query");
        if (StringUtils.isNotBlank(value)) {
            datasource.setValidationQuery(value);
        }
        value = environment.getProperty(path + "test-while-idle");
        if (StringUtils.isNotBlank(value)) {
            datasource.setTestWhileIdle(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "test-on-borrow");
        if (StringUtils.isNotBlank(value)) {
            datasource.setTestOnBorrow(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "test-on-return");
        if (StringUtils.isNotBlank(value)) {
            datasource.setTestOnReturn(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "pool-prepared-statements");
        if (StringUtils.isNotBlank(value)) {
            datasource.setPoolPreparedStatements(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "max-pool-prepared-statement-per-connection-size");
        if (StringUtils.isNotBlank(value)) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(value));
        }
        return datasource;
    }

    private List<Filter> handlerFilters(String path, String value) {
        String[] filters = value.split(",");
        List filterList = new ArrayList();
        for (String filter : filters) {
            if (StringUtils.isBlank(filter)) {
                continue;
            }
            filter = filter.trim();
            Filter filterInstance;
            if (filter.equals("stat")) {
                filterInstance = statFilter(path + "stat.");
            } else if (filter.equals("wall")) {
                filterInstance = wallFilter(path + "wall.");
            } else if (filter.equals("slf4j")) {
                filterInstance = logFilter(path + "slf4j.");
            } else {
                continue;
            }
            filterList.add(filterInstance);
        }
        return filterList;
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(SqlSessionFactoryCustomizers sqlSessionFactoryCustomizers, @Qualifier(value = "dynamicDatasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
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
        if (StringUtils.isNotBlank(enumPackages)) {
            factory.setTypeEnumsPackage(enumPackages);
        }
        factory.setGlobalConfig(globalConfiguration());
        sqlSessionFactoryCustomizers.customize(configuration, factory);
        factory.setTransactionFactory(new MultiDataSourceTransactionFactory());
        return factory.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 分布式事务使用JTA管理，不管有多少个数据源只要配置一个 JtaTransactionManager
     *
     */
//    /*atomikos事务管理器*/
//    public UserTransactionManager userTransactionManager() {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setForceShutdown(true);
//        return userTransactionManager;
//    }
//
//    public UserTransactionImp userTransactionImp() throws SystemException {
//        UserTransactionImp userTransactionImp = new UserTransactionImp();
//        userTransactionImp.setTransactionTimeout(5000);
//        return userTransactionImp;
//    }
//
//    @Bean
//    public JtaTransactionManager jtaTransactionManager() throws SystemException {
//        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//        jtaTransactionManager.setTransactionManager(userTransactionManager());
//        jtaTransactionManager.setUserTransaction(userTransactionImp());
//        jtaTransactionManager.setAllowCustomIsolationLevels(true);
//        return jtaTransactionManager;
//    }


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryCustomizers sqlSessionFactoryCustomizers(ObjectProvider<SqlSessionFactoryCustomizer<?, ?>> customizers) {
        return new SqlSessionFactoryCustomizers(customizers.stream().collect(Collectors.toList()));
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

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public StatFilter statFilter(String path) {
        StatFilter statFilter = new StatFilter();
        String value = environment.getProperty(path + "log-slow-sql");
        if (StringUtils.isNotBlank(value)) {
            statFilter.setLogSlowSql(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "merge-sql");
        if (StringUtils.isNotBlank(value)) {
            statFilter.setMergeSql(Boolean.getBoolean(value));
        }
        value = environment.getProperty(path + "slow-sql-millis");
        if (StringUtils.isNotBlank(value)) {
            statFilter.setSlowSqlMillis(Long.parseLong(value));
        }
        return statFilter;
    }

    public Slf4jLogFilter logFilter(String path) {
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

    public WallFilter wallFilter(String path) {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;

    }

    //
//    @Bean
//
    public WallConfig wallConfig() {
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);//允许一次执行多条语句
        config.setNoneBaseStatementAllow(true);//允许非基本语句的其他语句
        return config;

    }
}
