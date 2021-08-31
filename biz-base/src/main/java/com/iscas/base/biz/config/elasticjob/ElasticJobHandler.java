package com.iscas.base.biz.config.elasticjob;


import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.iscas.base.biz.util.SpringUtils;
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
    @Autowired(required = false)
    private JobEventConfiguration jobEventConfiguration;


    /**
     * 添加一个simple定时任务
     *
     * @param jobName                任务名
     * @param cron                   表达式
     * @param shardingTotalCount     分片数
     * @param jobParameter           任务的参数
     * @param shardingItemParameters 分片信息
     * @param clazz                  spring中已注册的SimpleJob类
     */
    public void addSimpleJob(String jobName, String cron, int shardingTotalCount, String jobParameter, String shardingItemParameters, Class<? extends SimpleJob> clazz) {
        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, clazz, shardingTotalCount, cron, jobParameter, shardingItemParameters)
                .overwrite(true).build();
        if (jobEventConfiguration == null) {
            new SpringJobScheduler(SpringUtils.getBean(clazz), zookeeperRegistryCenter, jobConfig, elasticJobListener).init();
        } else {
            new SpringJobScheduler(SpringUtils.getBean(clazz), zookeeperRegistryCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
        }
    }

    /**
     * <p>添加一个dataflow流式定时任务</p>
     * <p>需要注意的地方是，如果配置的是流式处理类型，它会不停的拉取数据、处理数据，</p>
     * <p>在拉取的时候，如果返回为空，就不会处理数据!</p>
     *
     * @param jobName                任务名
     * @param cron                   表达式
     * @param shardingTotalCount     分片数
     * @param jobParameter           任务的参数
     * @param shardingItemParameters 分片信息
     * @param clazz                  spring中已注册的SimpleJob类
     * @param streamingProcess       是否配置流式拉取
     */
    public void addDataFlowJob(String jobName, String cron, int shardingTotalCount, String jobParameter, String shardingItemParameters, boolean streamingProcess, Class<? extends DataflowJob> clazz) {
        LiteJobConfiguration jobConfig = dataFlowJobConfigBuilder(jobName, clazz, shardingTotalCount, cron, jobParameter, shardingItemParameters, streamingProcess)
                .overwrite(true).build();
        if (jobEventConfiguration == null) {
            new SpringJobScheduler(SpringUtils.getBean(clazz), zookeeperRegistryCenter, jobConfig, elasticJobListener).init();
        } else {
            new SpringJobScheduler(SpringUtils.getBean(clazz), zookeeperRegistryCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
        }
    }

    /**
     * 添加一个script定时任务
     *
     * @param jobName                任务名
     * @param cron                   表达式
     * @param shardingTotalCount     分片数
     * @param jobParameter           任务的参数
     * @param shardingItemParameters 分片信息
     * @param scriptCommandLine      脚本命令
     */
    public void addScriptJob(String jobName, String cron, int shardingTotalCount, String jobParameter, String shardingItemParameters, String scriptCommandLine) {
        LiteJobConfiguration jobConfig = scriptJobConfigBuilder(jobName, shardingTotalCount, cron, jobParameter, shardingItemParameters, scriptCommandLine)
                .overwrite(true).build();
        if (jobEventConfiguration == null) {
            new JobScheduler(zookeeperRegistryCenter, jobConfig, elasticJobListener).init();
        } else {
            new JobScheduler(zookeeperRegistryCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
        }
    }


    /**
     * @param jobName
     * @param jobClass
     * @param shardingTotalCount
     * @param cron
     * @param jobParameter           参数
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
     * @param jobName
     * @param jobClass
     * @param shardingTotalCount
     * @param cron
     * @param jobParameter           参数
     * @param shardingItemParameters 分片数据
     * @param streamingProcess 是否为流式拉取
     * @return
     */
    private static LiteJobConfiguration.Builder dataFlowJobConfigBuilder(String jobName,
                                                                         Class<? extends DataflowJob> jobClass,
                                                                         int shardingTotalCount,
                                                                         String cron,
                                                                         String jobParameter,
                                                                         String shardingItemParameters,
                                                                         boolean streamingProcess) {
        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters)
                        .jobParameter(jobParameter).build(), jobClass.getCanonicalName(), streamingProcess));
    }

    /**
     * @param jobName
     * @param shardingTotalCount
     * @param cron
     * @param jobParameter           参数
     * @param shardingItemParameters 分片数据
     * @param scriptCommandLine 执行脚本的命令语句
     * @return
     */
    private static LiteJobConfiguration.Builder scriptJobConfigBuilder(String jobName,
                                                                         int shardingTotalCount,
                                                                         String cron,
                                                                         String jobParameter,
                                                                         String shardingItemParameters,
                                                                         String scriptCommandLine) {
        return LiteJobConfiguration.newBuilder(new ScriptJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters)
                        .jobParameter(jobParameter).build(), scriptCommandLine));
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
