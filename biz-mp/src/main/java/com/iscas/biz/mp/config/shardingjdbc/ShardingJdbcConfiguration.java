package com.iscas.biz.mp.config.shardingjdbc;

import cn.hutool.core.map.MapUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.iscas.biz.mp.aop.enable.EnableAtomikos;
import com.iscas.biz.mp.config.db.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 21:29
 * @since jdk1.8
 */
@ConditionalOnClass(value = ShardingDataSourceFactory.class)
@Slf4j
public class ShardingJdbcConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {

    }

//    @Bean(name="shardingDataSource")
//    public DataSource shardingDataSource() throws SQLException {
//        String db = environment.getProperty("spring.datasource.sharding.names");
//        List<String> dbNames = Arrays.asList(db.split(","));
//        if (dbNames.size() == 0) {
//            log.error("没有数据源，无法使用数据库!!!");
//            return null;
//        }
//        Map<Object, Object> targetDataSources = new HashMap<>(2 << 2);
//        Map<String, Object> enableAtomikosMap = context.getBeansWithAnnotation(EnableAtomikos.class);
//        for (String dbName : dbNames) {
//            //初始化数据源
//            //update by zqw 20210520 添加Atomikos支持
//            DruidDataSource dataSource = initOneDatasource(dbName);
//            if (dataSource != null) {
//                if (MapUtil.isNotEmpty(enableAtomikosMap)) {
//                    //如果开启了Atomikos
//                    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//                    atomikosDataSourceBean.setXaDataSource((XADataSource) dataSource);
//                    //必须设置uniqueResourceName
//                    atomikosDataSourceBean.setUniqueResourceName(dbName);
//                    targetDataSources.put(dbName, atomikosDataSourceBean);
//                } else {
//                    //如果没开启Atomikos
//                    targetDataSources.put(dbName, dataSource);
//                }
//            }
//        }
//
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//
//        //设置数据源
//        if (targetDataSources.size() > 0) {
//            dynamicDataSource.setTargetDataSources(targetDataSources);
//            dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get(dbNames.get(0)));
//        }
//        dynamicDataSource.afterPropertiesSet();
//        return dynamicDataSource;

}
