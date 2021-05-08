package com.iscas.common.redis.tools.impl.jdk;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.JedisConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * JDK连接，不使用Redis,为了和其他的方式统一，也写了空的Connection
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/8 15:13
 * @since jdk1.8
 */
public class JdkNoneRedisConnection implements JedisConnection {

    /**存储分布式锁对象*/
    public TimedCache<String, String> ACQUIRE_LOCK_CACHE = null;

    /**对象缓存对象*/
    public TimedCache<String, Object> OBJECT_CACHE = null;

    //初始化
    public void init() {

        ACQUIRE_LOCK_CACHE = CacheUtil.newTimedCache(Long.MAX_VALUE);
        //启动定时任务，每5毫秒秒检查一次过期
        ACQUIRE_LOCK_CACHE.schedulePrune(5);

        OBJECT_CACHE = CacheUtil.newTimedCache(Long.MAX_VALUE);
        //启动定时任务，每5毫秒秒检查一次过期
        OBJECT_CACHE.schedulePrune(5);
    }

    @Override
    public Object getPool() {
        return null;
    }

    @Override
    public void initConfig(ConfigInfo configInfo) {

    }

    @Override
    public void close() {

    }
}
