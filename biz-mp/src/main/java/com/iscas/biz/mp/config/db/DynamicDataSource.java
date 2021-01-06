package com.iscas.biz.mp.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 13:47
 * @since jdk1.8
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 取得当前使用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }
}
