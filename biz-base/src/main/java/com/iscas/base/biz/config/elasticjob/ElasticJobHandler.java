package com.iscas.base.biz.config.elasticjob;


import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.iscas.base.biz.service.common.SpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * elasticJob处理类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/26 14:28
 * @since jdk1.8
 */
@ConditionalOnElasticJob()
@Component
public class ElasticJobHandler {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;


    @Autowired
    private ElasticJobListener elasticJobListener;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    /**
     * @param jobName
     * @param jobClass
     * @param shardingTotalCount
     * @param cron
     * @param jobParameter  参数
     * @param shardingItemParameters 分片数据
     * @return
     */
    private static LiteJobConfiguration.Builder simpleJobConfigBuilder(String jobName,
                                                                       Class<? extends SimpleJob> jobClass,
                                                                       int shardingTotalCount,
                                                                       String cron,
                                                                       String jobParameter,
                                                                       String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters)
                        .jobParameter(jobParameter).build(), jobClass.getCanonicalName()));
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName            任务名
     * @param cron               表达式
     * @param shardingTotalCount 分片数
     */
    public void addJob(String jobName, String cron, Integer shardingTotalCount, String jobParameter, String shardingItemParameters, Class<? extends SimpleJob> clazz) {
        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, clazz, shardingTotalCount, cron, jobParameter, shardingItemParameters)
                .overwrite(true).build();
//        new SpringJobScheduler(SpringService.getBean(clazz), zookeeperRegistryCenter, jobConfig, elasticJobListener).init();
        new SpringJobScheduler(SpringService.getBean(clazz), zookeeperRegistryCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
//        SpringJobScheduler jobScheduler=new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration,jobEventRdbConfiguration);
//        jobScheduler.init();
    }


//    public void addJob2(String jobName, String cron, Integer shardingTotalCount, String jobParameter, String shardingItemParameters, Class<? extends SimpleJob> clazz) {
//        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
//                .newBuilder(jobName, cron, shardingTotalCount)
//                .shardingItemParameters(shardingItemParameters)
//                .jobParameter(jobParameter)
//                .build();
//        SimpleJobConfiguration simpleJobConfiguration =
//                new SimpleJobConfiguration(jobCoreConfiguration, clazz.getCanonicalName());
//        JobScheduler jobScheduler = new JobScheduler(zookeeperRegistryCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).build());
//        jobScheduler.init();
//    }

}
