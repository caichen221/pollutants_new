package com.iscas.biz.mp.config.db;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.aop.enable.EnableAtomikos;
import com.iscas.biz.mp.aop.enable.EnableShardingJdbc;
import com.iscas.biz.mp.enhancer.injector.CustomSqlInjector;
import com.iscas.biz.mp.interfaces.IShardingJdbcHandler;
import com.iscas.common.tools.constant.CommonConstant;
import com.iscas.common.tools.constant.StrConstantEnum;
import com.iscas.common.tools.core.io.file.IoRaiseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
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
public class DruidConfiguration implements EnvironmentAware {

    @Value("${mybatis-plus.global-config.db-config.id-type}")
    private String idType;
    @Value("${mybatis-plus.global-config.db-config.logic-delete-value}")
    private String logicDeleteValue;
    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}")
    private String logicNotDeleteValue;
    @Value("${mybatis-plus.global-config.db-config.logic-delete-field}")
    private String logicDeleteField;
    @Value("${mybatis-plus.configuration.default-enum-type-handler}")
    private Class<? extends TypeHandler> defaultEnumTypeHandler;
    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;
    @Value("${mybatis-plus.gloabl-config.banner}")
    private boolean banner;

    @Value("${mybatis-plus.type-enums-package}")
    private String enumPackages;
    //    @Value("${mp.mapper.locations}")
//    private String mapMapperLocations;
    private final String basePath = "spring.datasource.druid.";
    private Environment environment;
    @Autowired
    private ApplicationContext context;

    @Bean(name = "dynamicDatasource")
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
                    //运行初始化脚本 add by zqw 2021-12-26
                    String path = basePath + dbName + ".";
                    String value = environment.getProperty(path + "initialization-mode");
                    if (Objects.equals("always", value)) {
                        value = environment.getProperty(path + "schema");
                        if (StringUtils.isNotEmpty(value)) {
                            runSchema(dataSource, value, dbName);
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
     */
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

        //密码加密判定
        value = environment.getProperty(path + "connect-properties.config.decrypt");
        if (Objects.equals("true", value)) {
            //解密
            String publicKey = environment.getProperty(path + "connect-properties.config.decrypt.key");
            value = environment.getProperty(path + "password");
            if (StringUtils.isBlank(value)) {
                log.error("数据源password不能为空");
                return null;
            }
            try {
                value = ConfigTools.decrypt(publicKey, value);
            } catch (Exception e) {
                log.error("密码:[" + value + "]解密失败", e);
            }
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
        value = environment.getProperty(path + "validation-query-timeout");
        if (StringUtils.isNotBlank(value)) {
            datasource.setValidationQueryTimeout(Integer.parseInt(value));
        }
        value = environment.getProperty(path + "num-tests-per-eviction-run");
        if (StringUtils.isNotBlank(value)) {
            datasource.setNumTestsPerEvictionRun(Integer.parseInt(value));
        }
        value = environment.getProperty(path + "min-evictable-idle-time-millis");
        if (StringUtils.isNotBlank(value)) {
            datasource.setMinEvictableIdleTimeMillis(Long.parseLong(value));
        }
        return datasource;
    }

    private static void runSchema(DataSource datasource, String schemas, String dbName) {
        InputStream is = null;
        try (Connection conn = datasource.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.setStopOnError(false);
            for (String schemaPath : schemas.split(StrConstantEnum.COMMA.getValue())) {
                schemaPath = schemaPath.trim();
                if (schemaPath.startsWith(CommonConstant.CLASSPATH)) {
                    ClassPathResource classPathResource = new ClassPathResource(StringUtils.substringAfter(schemaPath, CommonConstant.CLASSPATH));
                    is = classPathResource.getInputStream();
                } else {
                    is = new FileInputStream(schemaPath);
                }
                try (InputStreamReader isr = new InputStreamReader(is)) {
                    scriptRunner.runScript(isr);
                }
            }
        } catch (SQLException | IOException e) {
            log.warn(MessageFormat.format("数据源：{0}运行初始化脚本失败", dbName), e);
        }
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
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        configuration.setCacheEnabled(false);
        configuration.setDefaultEnumTypeHandler(defaultEnumTypeHandler);
        factory.setConfiguration(configuration);
//        factory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
//                paginationInterceptor() //添加分页功能
//        });
        PaginationInnerInterceptor paginationInnerInterceptor = paginationInterceptor();
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        factory.setPlugins(interceptor);
        if (StringUtils.isNotBlank(enumPackages)) {
            factory.setTypeEnumsPackage(enumPackages);
        }
        factory.setGlobalConfig(globalConfiguration());
        sqlSessionFactoryCustomizers.customize(configuration, factory);
        factory.setTransactionFactory(new MultiDataSourceTransactionFactory());
        //mapper config path
        String mpMapperLocations = environment.getProperty("mybatis-plus.mapper-locations");
        if (StringUtils.isNotEmpty(mpMapperLocations)) {
            Resource[] resources = Arrays.stream(mpMapperLocations.split(","))
                    .map(location -> {
                        try {
                            return new PathMatchingResourcePatternResolver().getResources(location);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).flatMap(Arrays::stream).toArray(Resource[]::new);
            factory.setMapperLocations(resources);
        }
        return factory.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 分布式事务使用JTA管理，不管有多少个数据源只要配置一个 JtaTransactionManager
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

//    @Bean
//    @ConditionalOnMissingBean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        return paginationInterceptor;
//    }

    /**
     * mybatis-plus新的分页插件注册方式
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }


    private GlobalConfig globalConfiguration() {
        GlobalConfig conf = GlobalConfigUtils.defaults();
        conf.getDbConfig().setIdType(IdType.valueOf(idType));
        conf.getDbConfig().setLogicDeleteValue(logicDeleteValue);
        conf.getDbConfig().setLogicNotDeleteValue(logicNotDeleteValue);
        conf.getDbConfig().setLogicDeleteField(logicDeleteField);

        conf.setSqlInjector(new CustomSqlInjector());
        conf.setBanner(banner);
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
