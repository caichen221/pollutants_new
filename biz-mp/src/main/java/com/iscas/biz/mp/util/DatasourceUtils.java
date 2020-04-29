package com.iscas.biz.mp.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 *
 * 获取datasource
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/17 8:54
 * @since jdk1.8
 */
public class DatasourceUtils {



    public DataSource getOracleDatasource(String ip, String port, String sid, String username, String pwd) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid);
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUsername(username);
        dataSource.setPassword(pwd);

//        dataSource.setInitialSize(20);
//        dataSource.setMinIdle(20);
//        dataSource.setMaxActive(50);
//        dataSource.setMaxWait(10000);
//        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        dataSource.setValidationQuery("");
        return dataSource;
    }
}
