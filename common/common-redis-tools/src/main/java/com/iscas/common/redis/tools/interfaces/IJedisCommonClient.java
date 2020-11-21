package com.iscas.common.redis.tools.interfaces;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.PipelineBase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/20 19:38
 * @since jdk1.8
 */
public interface IJedisCommonClient {



    /**
     * 获取分布式锁
     *
     * @param lockName        锁key
     * @param lockTimeoutInMS 锁超时时间
     * @return 锁标识
     */
    String acquireLock(String lockName, long lockTimeoutInMS);

    /**
     * 释放分布式锁
     *
     * @param lockName   锁key
     * @param identifier 锁标识
     * @return
     */
    boolean releaseLock(String lockName, String identifier);


    /**
     * 对IP进行限流
     */
    boolean accessLimit(String ip, int timeout, int limit);

    /**
     * 获取Pipeline
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param jedisResource Jedis 或SharedJedis 或JedisCluster
     * @throws
     * @return
     */
    PipelineBase getPipeline(Object jedisResource);

    /**
     * 使用pipeline批量执行，jedisCluster暂不支持
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param consumer
     * @throws
     * @return
     */
    void pipelineBatch(Consumer<PipelineBase> consumer);

}
