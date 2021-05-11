package com.iscas.common.redis.tools.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.helper.MyStringHelper;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import redis.clients.jedis.PipelineBase;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 不使用Redis，使用Jdk仿造Redis的方法，
 * 接口与Redis统一，适应不需要Redis的环境
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/08 12:53
 * @since jdk1.8
 */
public class JdkNoneRedisCommonClient {
    protected JedisConnection jedisConnection;
    protected JdkNoneRedisConnection jdkNoneRedisConnection;

    /**
     * 获取byte[]类型Key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) throws IOException {
        if(object instanceof String){
            return MyStringHelper.getBytes((String)object);
        }else{
            return MyObjectHelper.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object) throws IOException {
        return MyObjectHelper.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        return MyObjectHelper.unserialize(bytes);
    }

    /**
     * 获取分布式锁 返回Null表示获取锁失败
     * @version 1.0
     * @since jdk1.8
     * @date 2021/05/08
     * @param lockName 锁名称
     * @param lockTimeoutInMS 锁超时时间
     * @throws
     * @return java.lang.String 锁标识
     */
    public String acquireLock(String lockName, long lockTimeoutInMS) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;

        //TimeCache中已经使用了Lock，这里使用锁为了保证读取和存入缓存是一个原子操作
        synchronized (lockKey.intern()) {
            String lock = jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.get(lockKey);
            if (lock != null) {
                //锁存在
                return null;
            } else {
                //锁不存在
                jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.put(lockKey, identifier, lockTimeoutInMS);
            }
        }
        return identifier;
    }

    /**
     * 释放锁
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param lockName 锁key
     * @param identifier 锁标识
     * @throws
     * @return boolean
     */
    public boolean releaseLock(String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        boolean result;
        if (identifier == null) {
            result = true;
        }
        //TimeCache中已经使用了Lock，这里使用锁为了保证读取和删除缓存是一个原子操作
        synchronized (lockKey.intern()) {
            String identifier2 = jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.get(lockKey);
            if (null != identifier2 && !Objects.equals(identifier, identifier2)) {
                //只有加锁的人才能释放锁，如果获取的值跟传入的
                //identifier不一致，说明这不是自己加的锁，拒绝释放
                result = false;
            } else {
                jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.remove(lockKey);
                result = true;
            }
        }
        return result;
    }

    /**
     *  IP 限流
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param ip ip
     * @param timeout 规定时间 （秒）
     * @param limit 限制次数
     * @throws
     * @return 是否可以访问
     */
    public boolean accessLimit(String ip, int timeout, int limit) {
        throw new UnsupportedOperationException("jdk模式不支持IP限流");
    }

    public Object jedisCommandsBytesLuaEvalSha(Object jedisCommands, String lua, List key, List val) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    public PipelineBase getPipeline(Object jedisResource) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    public void pipelineBatch(Consumer<PipelineBase> consumer) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    protected long doDel(String key) {
        synchronized (key.intern()) {
            if (jdkNoneRedisConnection.OBJECT_CACHE.containsKey(key)) {
                jdkNoneRedisConnection.OBJECT_CACHE.remove(key);
                return 1;
            } else {
                return 0;
            }
        }
    }

    protected boolean doExists(String key) {
        return jdkNoneRedisConnection.OBJECT_CACHE.containsKey(key);
    }

    protected void doDeleteByPattern(String pattern) {
        //转换为正则表达式
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replace("?", ".");
        Map cacheMap = (Map) ReflectUtil.getFieldValue(jdkNoneRedisConnection.OBJECT_CACHE, "cacheMap");
        Set<String> set = cacheMap.keySet();
        if (CollectionUtil.isNotEmpty(set)) {
            Pattern pa = Pattern.compile(pattern);
            //删除正则表达式匹配到得key
            set.stream().filter(key -> pa.matcher(key).matches())
                    .forEach(jdkNoneRedisConnection.OBJECT_CACHE::remove);
        }
    }

    protected void doExpire(String key, long milliseconds) {
        Object value = jdkNoneRedisConnection.OBJECT_CACHE.get(key);
        if (value != null) {
            jdkNoneRedisConnection.OBJECT_CACHE.put(key, value, milliseconds);
        }
    }

    protected boolean doSet(String key, Object value, long seconds) {
        if (seconds == 0) {
            jdkNoneRedisConnection.OBJECT_CACHE.put(key, value);
        } else {
            jdkNoneRedisConnection.OBJECT_CACHE.put(key, value, seconds * 1000);
        }
        return true;
    }

    protected <T> T doGet(Class<T> tClass, String key) {
        return (T) jdkNoneRedisConnection.OBJECT_CACHE.get(key);
    }

    protected long doSetnx(String key, Object value) {
        synchronized (key.intern()) {
            if (doExists(key)) {
                return 0L;
            }
            if (doSet(key, value, 0)) {
                return 1L;
            }
            return 0L;
        }
    }

    protected <T> T doGetSet(Class<T> tClass, String key, T value) {
        synchronized (key.intern()) {
            T s = doGet(tClass, key);
            doSet(key, value, 0);
            return s;
        }
    }

    protected <T> List<T> doMget(Class<T> tClass, String... keys) {
        return Arrays.stream(keys).map(key -> doGet(tClass, key))
                .collect(Collectors.toList());
    }

}
