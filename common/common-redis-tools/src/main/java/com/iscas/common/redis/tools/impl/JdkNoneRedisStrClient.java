package com.iscas.common.redis.tools.impl;


import com.iscas.common.redis.tools.IJedisStrClient;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * JdkStrClient
 *
 * 使用Jdk模拟redis的操作，这里不需要依赖redis,适用不用Redis的环境，但可用Redis的接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/08
 * @since jdk1.8
 */
public class JdkNoneRedisStrClient extends JdkNoneRedisCommonClient implements IJedisStrClient {

    public JdkNoneRedisStrClient(JedisConnection jedisConnection) {
        this.jedisConnection = jedisConnection;
        this.jdkNoneRedisConnection = (JdkNoneRedisConnection) jedisConnection;
        jdkNoneRedisConnection.init();
    }


    /*========================通用 begin=================================*/

    @Override
    public void pipelineClusterBatch(Consumer<RedisAdvancedClusterCommands<String, String>> consumer) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }
    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    @Override
    public long del(String key) {
        return doDel(key);
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    @Override
    public boolean exists(String key) {
        return doExists(key);
    }

    /**
     * 模糊删除
     * */
    @Override
    public void deleteByPattern(String pattern) {
        doDeleteByPattern(pattern);
    }

    @Override
    public void expire(String key, long milliseconds) {
        doExpire(key, milliseconds);
    }

    @Override
    public void putDelayQueue(String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
        //使用默认key
//        String hostAddress = null;
//        try {
//            InetAddress localHost = InetAddress.getLocalHost();
//            hostAddress = localHost.getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        putDelayQueue(DELAY_QUEUE_DEFUALT_KEY.concat(hostAddress), task, timeout, timeUnit, consumer);
    }

    @Override
    public void putDelayQueue(String key, String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
//        long l = System.currentTimeMillis();
//        long x = timeUnit.toMillis(timeout);
//        long targetScore = l + x;
//        Map<String, Double> map = new HashMap();
//        map.put(task, Double.valueOf(String.valueOf(targetScore)));
//        zadd(key, map);
//        MAP_DELAY.put(task, consumer);
//        delayTaskHandler(key);
    }

    private void delayTaskHandler(String key) {
//        Object jedis = null;
//        try {
//            jedis = getResource(Object.class);
//            if (MAP_DELAY_EXECUTE.get(key) == null) {
//                synchronized (key.intern()) {
//                    if (MAP_DELAY_EXECUTE.get(key) == null) {
//                        MAP_DELAY_EXECUTE.put(key, true);
//                        while (true) {
//                            Map<String, Double> zSet = zrangeWithScoresToMap(key, 0, -1);
//                            if (zSet == null || zSet.size() == 0) {
//                                break;
//                            }
//                            for (Map.Entry<String, Double> entry : zSet.entrySet()) {
//                                String storeTask = entry.getKey();
//                                Double score = entry.getValue();
//                                if (System.currentTimeMillis() - score > 0) {
//                                    //开始执行任务
//                                    MAP_DELAY.get(storeTask).accept(storeTask);
//                                    //删除这个值
//                                    String script = "return redis.call('zrem', KEYS[1], ARGV[1])";
//                                    jedisCommandsBytesLuaEvalSha(jedis, script, Collections.singletonList(key), Collections.singletonList(storeTask));
//                                }
//                            }
//                            try {
//                                TimeUnit.SECONDS.sleep(1);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        } finally {
//            returnResource(jedis);
//        }

    }

    /*========================通用 end  =================================*/



    /*=============================set begin===============================================*/
    @Override
    public long sadd(String key, String... value) {
        return doSadd(key, value);
    }

    @Override
    public long scard(String key) {
        return doScard(key);
    }

    @Override
    public Set<String> sdiff(String... keys) {
        return doSdiff(String.class, keys);
    }

    @Override
    public long sdiffStore(String newkey, String... keys) {
        return doSdiffStore(newkey, keys);
    }

    @Override
    public Set<String> sinter(String... keys) {
        return doSinter(String.class, keys);
    }

    @Override
    public long sinterStore(String newKey, String... keys) {
        return doSinterStore(newKey, keys);
    }

    @Override
    public boolean sismember(String key, String member) {
        return doSismember(key, member);
    }

    @Override
    public Set<String> smembers(String key) {
        return doSmembers(String.class, key);
    }

    @Override
    public long smove(String srckey, String dstkey, String member) {
        return doSmove(srckey, dstkey, member);
    }

    @Override
    public String spop(String key) {
        return doSpop(String.class, key);
    }

    @Override
    public Set<String> spop(String key, long count) {
        return doSpop(String.class, key, count);
    }

    @Override
    public long srem(String key, String... member) {
        return doSrem(key, member);
    }

    @Override
    public Set<String> sunion(String... keys) {
        return doSunion(String.class, keys);
    }

    /*=============================set end  ===============================================*/

    /*==============================sort set begin=====================================================*/
    @Override
    public long zadd(String key, double score, String member) {
//        Object jc = null;
//        try {
//            jc = getResource(Object.class);
//            if (jc instanceof Jedis) {
//                Jedis jedis = (Jedis) jc;
//                return jedis.zadd(key, score, member);
//            } else if (jc instanceof ShardedJedis) {
//                ShardedJedis shardedJedis = (ShardedJedis) jc;
//                shardedJedis.zadd(key, score, member);
//            } else if (jc instanceof JedisCluster) {
//                JedisCluster jedisCluster = (JedisCluster) jc;
//                return jedisCluster.zadd(key, score, member);
//            }
//            return 0;
//        } finally {
//            returnResource(jc);
//        }
        return 0L;
    }

    @Override
    public long zadd(String key, Map<String, Double> valueScoreMap, int cacheSeconds) {
//        long result = 0;
//        Object jc = null;
//        try {
//            jc = getResource(Object.class);
//            if (valueScoreMap == null || valueScoreMap.size() == 0 ) {
//                throw new RuntimeException("集合不能为空");
//            }
//            if (jc instanceof Jedis) {
//                Jedis jedis = (Jedis) jc;
//                if (jedis.exists(key)) {
//                    jedis.del(key);
//                }
//                result = jedis.zadd(key, valueScoreMap);
//                if (cacheSeconds != 0) {
//                    jedis.expire(key, cacheSeconds);
//                }
//            } else if (jc instanceof ShardedJedis) {
//                ShardedJedis jedis = (ShardedJedis) jc;
//                if (jedis.exists(key)) {
//                    jedis.del(key);
//                }
//                result = jedis.zadd(key, valueScoreMap);
//                if (cacheSeconds != 0) {
//                    jedis.expire(key, cacheSeconds);
//                }
//            } else if (jc instanceof JedisCluster) {
//                JedisCluster jedis = (JedisCluster) jc;
//                if (jedis.exists(key)) {
//                    jedis.del(key);
//                }
//                result = jedis.zadd(key, valueScoreMap);
//                if (cacheSeconds != 0) {
//                    jedis.expire(key, cacheSeconds);
//                }
//            }
//
//        } finally {
//            returnResource(jc);
//        }
//        return result;
        return 0L;
    }

    @Override
    public long zadd(String key, Map<String, Double> valueScoreMap) {
//        Object jc = null;
//        try {
//            jc = getResource(Object.class);
//            if (jc instanceof Jedis) {
//                Jedis jedis = (Jedis) jc;
//                return jedis.zadd(key, valueScoreMap);
//            } else if (jc instanceof ShardedJedis) {
//                ShardedJedis shardedJedis = (ShardedJedis) jc;
//                shardedJedis.zadd(key, valueScoreMap);
//            } else if (jc instanceof JedisCluster) {
//                JedisCluster jedisCluster = (JedisCluster) jc;
//                return jedisCluster.zadd(key, valueScoreMap);
//            }
//            return 0;
//        } finally {
//            returnResource(jc);
//        }
        return 0L;
    }

    @Override
    public long zcard(String key) {
        return 0L;
    }

    @Override
    public long zcount(String key, double min, double max) {
        return 0L;
    }

    @Override
    public double zincrby(String key, double score, String member) {
        return 0.0;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Map<String, Double> zrangeWithScoresToMap(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    }

    @Override
    public Map<String, Double> zrangeByScoreWithScoresToMap(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Map<String, Double> zrangeByScoreWithScoresToMap(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public long zrank(String key, String member) {
        return 0L;
    }

    @Override
    public long zrevrank(String key, String member) {
        return 0L;
    }

    @Override
    public long zrem(String key, String... members) {
        return 0L;
    }

    @Override
    public long zremrangeByRank(String key, int start, int end) {
        return 0L;
    }

    @Override
    public long zremrangeByScore(String key, double min, double max) {
        return 0L;
    }

    @Override
    public Double zscore(String key, String memeber) {
        return 0.0;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return null;
    }

    @Override
    public long zremrangeByLex(String key, String min, String max) {
        return 0L;
    }

    @Override
    public long zinterstore(String dstKey, String... keys) {
        return 0L;
    }

    @Override
    public long zunionstore(String dstKey, String... keys) {
        return 0L;
    }

    /*==============================sort set end  =====================================================*/


    /*==============================hash begin  =====================================================*/

    @Override
    public boolean hmset(String key, Map<String, String> map, int cacheSeconds) {
        return doHmset(key, map, cacheSeconds);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return doHgetAll(String.class, String.class, key);
    }

    @Override
    public long hdel(String key, String... fields) {
        return doHdel(key, fields);
    }

    @Override
    public boolean hexists(String key, String field) {
        return doHexists(key, field);
    }

    @Override
    public String hget(String key, String field) {
        return doHget(String.class, key, field);
    }

    @Override
    public long hset(String key, String field, String value) {
        return doHset(key, field, value);
    }

    @Override
    public long hsetnx(String key, String field, String value) {
        return doHsetnx(key, field, value);
    }

    @Override
    public List<String> hvals(String key) {
        return doHvals(String.class, key);
    }

    @Override
    public long hincrby(String key, String field, long value) {
        return doHincrby(key, field, value);
    }

    @Override
    public Double hincrby(String key, String field, double value) {
        return doHincrby(key, field, value);
    }

    @Override
    public Set<String> hkeys(String key) {
        return doHkeys(String.class, key);
    }

    @Override
    public long hlen(String key) {
        return doHlen(key);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return doHmget(String.class, key, fields);
    }

    /*==============================hash end=====================================================*/


    /*==============================string begin=====================================================*/
    /**
     * 设置数据，字符串数据
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public boolean set(String key, String value, int cacheSeconds) {
        synchronized (key.intern()) {
            return doSet(key, value, cacheSeconds);
        }
    }

    /**
     * 获取数据，获取字符串数据
     * @param key 键
     * @return 值
     */
    @Override
    public String get(String key) {
        return doGet(String.class, key);
    }

    @Override
    public long setnx(String key, String value) {
        return doSetnx(key, value);
    }

    @Override
    public long setrange(String key, long offset, String value) {
        synchronized (key.intern()) {
            String s = doGet(String.class, key);
            if (s == null || offset > s.length() - 1) {
                return 0L;
            }
            String prefix = s.substring(0, (int) offset);
            StringBuilder resStr = new StringBuilder(prefix);
            resStr.append(value);
            if (s.length() > resStr.length()) {
                String suffix = key.substring(resStr.length());
                resStr.append(suffix);
            }
            boolean res = doSet(key, resStr.toString(), 0);
            return res ? resStr.length() : 0L;
        }
    }

    @Override
    public long append(String key, String value) {
        synchronized (key.intern()) {
            String s = doGet(String.class, key);
            if (s == null || value.length() == 0) {
                return 0L;
            }
            String newStr = s + value;
            boolean res = doSet(key, newStr, 0);
            return res ? newStr.length() : 0L;
        }
    }

    @Override
    public long decrBy(String key, long number) {
        synchronized (key.intern()) {
            String s = doGet(String.class, key);
            if (s == null) {
                throw new RuntimeException(String.format("未找到key：%s对应的值", key));
            }
            try {
                long l = Long.parseLong(s);
                return l - number;
            } catch (Exception e) {
                throw new RuntimeException(String.format("无法将值:%s转为数字", s), e);
            }
        }
    }

    @Override
    public long incrBy(String key, long number) {
        return decrBy(key, -number);
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        synchronized (key.intern()) {
            String s = doGet(String.class, key);
            if (s == null) {
                throw new RuntimeException(String.format("未找到key：%s对应的值", key));
            }
            return s.substring((int) startOffset, (int) endOffset + 1);
        }
    }

    @Override
    public String getSet(String key, String value) {
        return doGetSet(String.class, key, value);
    }

    @Override
    public List<String> mget(String... keys) {
        return doMget(String.class, keys);
    }

    @Override
    public boolean mset(String... keysvalues) {
        return doMset(keysvalues);
    }

    @Override
    public long strlen(String key) {
        String s = get(key);
        return s == null ? 0L : s.length();
    }
    /*==============================string end=====================================================*/


    /*==============================list begin=====================================================*/
    @Override
    public long rpush(String key, String... value) {
        return doRpush(key, value);
    }

    @Override
    public long lpush(String key, String... value) {
        return doRpush(key, value);
    }

    @Override
    public long llen(String key) {
        return doLlen(key);
    }

    @Override
    public boolean lset(String key, int index, String value) {
        return doLset(key, index, value);
    }

    @Override
    public long linsert(String key, ListPosition where, String pivot, String value) {
        return doLinsert(key, where, pivot, value);
    }

    @Override
    public String lindex(String key, long index) {
        return doLindex(String.class, key, index);
    }

    @Override
    public String lpop(String key) {
        return doLpop(String.class, key);
    }

    @Override
    public String rpop(String key) {
        return doRpop(String.class, key);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return doLrange(String.class, key, start, end);
    }

    @Override
    public long lrem(String key, int count, String value) {
        return doLrem(key, count, value);
    }

    @Override
    public boolean ltrim(String key, long start, long end) {
        return doLtrim(key, start, end);
    }
    /*==============================list end=======================================================*/




}
