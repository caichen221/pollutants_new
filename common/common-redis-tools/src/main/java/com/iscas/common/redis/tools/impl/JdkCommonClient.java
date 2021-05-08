package com.iscas.common.redis.tools.impl;

import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.helper.MyStringHelper;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import redis.clients.jedis.PipelineBase;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 不使用Redis，使用Jdk仿造Redis的方法，
 * 接口与Redis统一，适应不需要Redis的环境
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/08 12:53
 * @since jdk1.8
 */
public class JdkCommonClient {
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
//        Object conn = null;
//        try {
//            conn = getResource(Object.class);
//            String lua = "local num = redis.call('incr', KEYS[1])\n" +
//                    "if tonumber(num) == 1 then\n" +
//                    "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
//                    "\treturn 1\n" +
//                    "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
//                    "\treturn 0\n" +
//                    "else \n" +
//                    "\treturn 1\n" +
//                    "end\n";
//            Object result = jedisCommandsBytesLuaEvalSha(conn, lua, Arrays.asList(ip), Arrays.asList(String.valueOf(timeout),
//                    String.valueOf(limit)));
//            return "1".equals(result == null ? null : result.toString());
//        }  finally {
//            returnResource(conn);
//        }
        return false;
    }

    public Object jedisCommandsBytesLuaEvalSha(Object jedisCommands, String lua, List key, List val) {
//        if (jedisCommands instanceof Jedis) {
//            Jedis jedis = (Jedis) jedisCommands;
//            return jedis.evalsha(jedis.scriptLoad(lua), key, val);
//        } else if (jedisCommands instanceof ShardedJedis) {
//            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
//            throw new RuntimeException("ShardedJedis 暂不支持执行Lua脚本");
//        } else if (jedisCommands instanceof JedisCluster) {
//            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
//            return jedisCluster.evalsha(jedisCluster.scriptLoad(lua, lua), key, val);
//        }
        return 0;
    }

    public PipelineBase getPipeline(Object jedisResource) {
//        if (jedisResource instanceof Jedis) {
//            Jedis jedis = (Jedis) jedisResource;
//            return jedis.pipelined();
//        } else if (jedisResource instanceof ShardedJedis) {
//            ShardedJedis shardedJedis = (ShardedJedis) jedisResource;
//            return shardedJedis.pipelined();
//        } else if (jedisResource instanceof JedisCluster) {
//            JedisCluster jedisCluster = (JedisCluster) jedisResource;
//            throw new RuntimeException("JedisCluster 暂不支持pipeline");
//        }
//        throw new RuntimeException("jedisResource:" + jedisResource.getClass().getSimpleName() + " 为不支持的类型");
        return null;
    }

    public void pipelineBatch(Consumer<PipelineBase> consumer) {
//        Object jc = null;
//        try {
//            jc = getResource(Object.class);
//            PipelineBase pipeline = getPipeline(jc);
//            consumer.accept(pipeline);
//            if (pipeline instanceof Pipeline) {
//                ((Pipeline) pipeline).sync();
//            } else if (pipeline instanceof ShardedJedisPipeline) {
//                ((ShardedJedisPipeline) pipeline).sync();
//            }
//        } finally {
//            returnResource(jc);
//        }
    }


}
