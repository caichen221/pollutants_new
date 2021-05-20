package com.iscas.biz.config.shedlock;

import com.alibaba.druid.pool.DruidDataSource;
import com.iscas.base.biz.config.shedlock.ConditionalOnShedLock;
import com.iscas.base.biz.config.shedlock.ShedLockDatasourceCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 生成一个datasource
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/20 19:56
 * @since jdk1.8
 */
@Component
@ConditionalOnShedLock
public class MyShedLockDatasourceCreator implements ShedLockDatasourceCreator {
    @Value("${spring.datasource.druid.mysql1.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.druid.mysql1.url}")
    private String url;
    @Value("${spring.datasource.druid.mysql1.username}")
    private String username;
    @Value("${spring.datasource.druid.mysql1.password}")
    private String password;



    @Override
    public DataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
}
