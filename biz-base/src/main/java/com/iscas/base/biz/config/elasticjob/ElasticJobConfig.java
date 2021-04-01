package com.iscas.base.biz.config.elasticjob;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.DruidDataSourceUtils;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.iscas.base.biz.service.common.SpringService;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * elasticJob配置
 *
 * <p>elasticJob配置</p>
 * <p>参考：https://blog.csdn.net/oppo5630/article/details/79963490</p>
 * <p>参考：https://developer.51cto.com/art/202101/643302.htm?mobile</p>
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/26 14:28
 * @since jdk1.8
 */
//@Configuration
@ConditionalOnExpression("'${elastic.zookeeper.server-lists}'.length() >0")
@Import(ElasticDataSourceConfig.class)
@Lazy(value = false)
public class ElasticJobConfig {
    @Resource(name = "elasticDatasource")
    private DataSource dataSource;

    /**
     * 初始化配置
     * */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") String serverList
            , @Value("${elaticjob.zookeeper.namespace}") String namespace) {

        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    /**
     * 设置活动监听
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new MyElasticJobListener(100, 100);
    }


    /**
     * 将作业运行的痕迹进行持久化到DB
     *
     * @return
     */
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }

}

