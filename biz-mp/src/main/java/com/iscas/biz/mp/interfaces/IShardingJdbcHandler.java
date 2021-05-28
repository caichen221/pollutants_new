package com.iscas.biz.mp.interfaces;

import com.iscas.biz.mp.config.db.MultiDatasourceAspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import javax.sql.DataSource;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/28 21:56
 * @since jdk1.8
 */
public interface IShardingJdbcHandler {
    Map<String, DataSource> initShardingDatasource();
    void registShardingAspect(BeanDefinitionRegistry registry, MultiDatasourceAspectJExpressionPointcutAdvisor multiDatasourceAspectJExpressionPointcutAdvisor);
}
