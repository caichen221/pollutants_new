//package com.iscas.base.biz.config.elasticjob;
//
//import com.dangdang.ddframe.job.event.JobEventConfiguration;
//import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
//import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Lazy;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * elasticJob配置
// *
// * <p>elasticJob配置</p>
// * <p>参考：https://blog.csdn.net/oppo5630/article/details/79963490</p>
// * <p>参考：https://developer.51cto.com/art/202101/643302.htm?mobile</p>
// *
// * <p>监控页面elasticJob-console的使用：</p>
// * <p>1、在special下打开模块elastic-job-liete-console模块</p>
// * <p>2、运行ConsoleBootstrap类的主函数</p>
// * <p>3、进入http://localhost:8899</p>
// * <p>4、全局配置-注册中心配置，添加zookeeper相关信息</p>
// * <p>5、全局配置-时间追踪数据源配置，添加数据源mysql相关配置</p>
// * <p>6、可以通过监控或修改定时任务了</p>
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2021/3/26 14:28
// * @since jdk1.8
// */
////@Configuration
//@ConditionalOnExpression("'${elastic.zookeeper.server-lists}'.length() >0")
//@Import(ElasticDataSourceConfig.class)
//@Lazy(value = false)
//public class ElasticJobConfig {
//
//    @Autowired(required = false)
//    @Qualifier("elasticDatasource")
//    private DataSource dataSource;
//
//    /**
//     * 初始化配置
//     * */
//    @Bean(initMethod = "init")
//    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") String serverList
//            , @Value("${elaticjob.zookeeper.namespace}") String namespace) {
//
//        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
//    }
//
//    /**
//     * 设置活动监听
//     */
//    @Bean
//    public ElasticJobListener elasticJobListener() {
//        return new MyElasticJobListener( 30 * 60000, 30 * 60000);
//    }
//
//
//    /**
//     * 将作业运行的痕迹进行持久化到DB
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnElasticJobWithDatasource
//    public JobEventConfiguration jobEventConfiguration() {
//        return new JobEventRdbConfiguration(dataSource);
//    }
//
//}
//
