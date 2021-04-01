package com.iscas.base.biz.config.elasticjob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/26 14:28
 * @since jdk1.8
 */
//@Component
@Slf4j
public class MyElasticJobListener extends AbstractDistributeOnceElasticJobListener {


    /**
     * 设置间隔时间
     * @param startedTimeoutMilliseconds
     * @param completedTimeoutMilliseconds
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MyElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    /**
     * 任务开始
     * @param shardingContexts
     */
    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        log.debug("任务:{}开始，任务分片数：{}，任务分片：{}，参数：{}", shardingContexts.getJobName(),
                shardingContexts.getShardingTotalCount(), shardingContexts.getShardingItemParameters(),
                shardingContexts.getJobParameter());
    }

    /**
     * 任务结束
     * @param shardingContexts
     */
    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        log.debug("任务:{}结束，任务分片数：{}，任务分片：{}，参数：{}", shardingContexts.getJobName(),
                shardingContexts.getShardingTotalCount(), shardingContexts.getShardingItemParameters(),
                shardingContexts.getJobParameter());
    }
}
