package com.iscas.common.redis.tools.impl;


import com.iscas.common.redis.tools.IJedisClient;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.*;

/**
 * JdkClient
 *
 * 使用Jdk模拟redis的操作，这里不需要依赖redis,适用不用Redis的环境，但可用Redis的接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/08
 * @since jdk1.8
 */
public class JdkNoneRedisClient extends JdkNoneRedisCommonClient implements IJedisClient {

    public JdkNoneRedisClient(JedisConnection jedisConnection) {
        this.jedisConnection = jedisConnection;
        this.jdkNoneRedisConnection = (JdkNoneRedisConnection) jedisConnection;
        jdkNoneRedisConnection.init();
    }

    /*=============================通用 begin=========================================*/
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
    public boolean exists(String key) throws IOException {
        return doExists(key);
    }

    private boolean jedisCommandsBytesExists(Object jedisCommands, byte[] bytesKey) {
        return false;
    }

    private byte[] jedisCommandsBytesGet(Object jedisCommands, byte[] bytesKey) {
        return null;
    }

    private String jedisCommandsBytesSet(Object jedisCommands, byte[] bytesKey, byte[] bytesValue) {
        return null;
    }

    private Set<byte[]> jedisCommandsBytesSmembers(Object jedisCommands, byte[] bytesKey) {
        return null;
    }

    private long  jedisCommandsBytesSadd(Object jedisCommands, byte[] bytesKey, byte[][] bytesValue) {
        return 0L;
    }

    private long  jedisCommandsBytesDel(Object jedisCommands, byte[] bytesKey) {
        return 0L;
    }

    /**
     * 模糊删除
     * */
    @Override
    public void deleteByPattern(String pattern) {
        doDeleteByPattern(pattern);
    }

//    @Override
//    public void putDelayQueue(String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
//        //使用默认key
//        String hostAddress = null;
//        try {
//            InetAddress localHost = InetAddress.getLocalHost();
//            hostAddress = localHost.getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        putDelayQueue(DELAY_QUEUE_DEFUALT_KEY.concat(hostAddress), task, timeout, timeUnit, consumer);
//    }

//    @Override
//    public void putDelayQueue(String key, String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
//        long l = System.currentTimeMillis();
//        long x = timeUnit.toMillis(timeout);
//        long targetScore = l + x;
//        Map<String, Double> map = new HashMap();
//        map.put(task, Double.valueOf(String.valueOf(targetScore)));
//        setZSetAdd(key, map);
//        MAP_DELAY.put(task, consumer);
//        delayTaskHandler(key);
//    }



    @Override
    public void expire(String key, long milliseconds) throws IOException {
        doExpire(key, milliseconds);
    }
    /*=============================通用 end==========================================*/

    /*========================================set begin========================================================*/
    /**
     * 设置Set，值为任意对象类型
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public long sadd(String key, Set value, int cacheSeconds) throws IOException {
       return doSadd(key, value, cacheSeconds);
    }

    /**
     * 向Set中追加值，类型为对象
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public long sadd(String key, Object... value) throws IOException {
        return doSadd(key, value);
    }

    @Override
    public long scard(String key) throws IOException {
       return doScard(key);
    }

    @Override
    public <T> Set<T> sdiff(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return doSdiff(tClass, keys);
    }

    @Override
    public long sdiffStore(String newkey, String... keys) throws IOException, ClassNotFoundException {
        return doSdiffStore(newkey, keys);
    }

    @Override
    public <T> Set<T> sinter(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return doSinter(tClass, keys);
    }

    @Override
    public long sinterStore(String newKey, String... keys) throws IOException {
        return doSinterStore(newKey, keys);
    }

    @Override
    public boolean sismember(String key, Object member) throws IOException {
        return doSismember(key, member);
    }

    @Override
    public <T> Set<T> smembers(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doSmembers(tClass, key);
    }

    @Override
    public long smove(String srckey, String dstkey, Object member) throws IOException {
        return doSmove(srckey, dstkey, member);
    }

    @Override
    public <T> T spop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doSpop(tClass, key);
    }

    @Override
    public <T> Set<T> spop(Class<T> tClass, String key, long count) throws IOException, ClassNotFoundException {
        return doSpop(tClass, key, count);
    }

    @Override
    public long srem(String key, Object... member) throws IOException {
        return doSrem(key, member);
    }

    @Override
    public <T> Set<T> sunion(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return doSunion(tClass, keys);
    }

    /*========================================set end  ========================================================*/

    /*===========================sort set begin========================================*/
    @Override
    public long zadd(String key, double score, Object member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zadd(String key, Map<? extends Object, Double> valueScoreMap, int cacheSeconds) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zadd(String key, Map<? extends Object, Double> valueScoreMap) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zcard(String key) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zcount(String key, double min, double max) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public double zincrby(String key, double score, Object member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Set<T> zrange(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Map<T, Double> zrangeWithScoresToMap(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public  <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max)  throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zrank(String key, Object member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zrevrank(String key, Object member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zrem(String key, Object... members) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zremrangeByRank(String key, int start, int end) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zremrangeByScore(String key, double min, double max) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public Double zscore(String key, Object memeber) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zinterstore(String dstKey, String... keys) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long zunionstore(String dstKey, String... keys) throws IOException {
        throw new UnsupportedOperationException();

    }

    /*===========================sort set end==========================================*/


    /*===========================hash begin==========================================*/

    @Override
    public boolean hmset(String key, Map map, int cacheSeconds) throws IOException {
        return doHmset(key, map, cacheSeconds);
    }

    @Override
    public <K extends Object, V extends Object> Map<K, V> hgetAll(Class<K> keyClass, Class<V> valClass, String key) throws IOException, ClassNotFoundException {
        return doHgetAll(keyClass, valClass, key);
    }

    @Override
    public long hdel(String key, Object... fields) throws IOException {
        return doHdel(key, fields);
    }

    @Override
    public boolean hexists(String key, Object field) throws IOException {
        return doHexists(key, field);
    }

    @Override
    public <T> T hget(Class<T> tClass, String key, String field) throws IOException, ClassNotFoundException {
        return doHget(tClass, key, field);
    }

    @Override
    public long hset(String key, Object field, Object value) throws IOException {
        return doHset(key, field, value);
    }

    @Override
    public long hsetnx(String key, Object field, Object value) throws IOException {
        return doHsetnx(key, field, value);
    }

    @Override
    public <T> List<T> hvals(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doHvals(tClass, key);
    }

    @Override
    public long hincrby(String key, String field, long value) throws IOException {
        return doHincrby(key, field, value);
    }

    @Override
    public Double hincrby(String key, String field, double value) throws IOException {
        return doHincrby(key, field, value);
    }

    @Override
    public <T> Set<T> hkeys(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doHkeys(tClass, key);
    }

    @Override
    public long hlen(String key) throws IOException {
        return doHlen(key);
    }

    @Override
    public <T> List<T> hmget(Class<T> tClass, String key, Object... fields) throws IOException, ClassNotFoundException {
       return doHmget(tClass, key, fields);
    }

    /*===========================hash end==========================================*/

    /*===========================string begin==========================================*/
    /**
     * 设置数据，对象数据，序列化后存入redis
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public boolean set(String key, Object value, int cacheSeconds) throws IOException {
        return doSet(key, value, cacheSeconds);
    }

    @Override
    public <T> T get(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doGet(tClass, key);
    }

    @Override
    public long setnx(String key, Object value) throws IOException {
        return doSetnx(key, value);
    }

    @Override
    public long setrange(String key, long offset, Object value) throws IOException {
        throw new UnsupportedOperationException("不支持此setrange操作");
    }

    @Override
    public long append(String key, String value) {
        throw new UnsupportedOperationException("不支持此append操作");

    }

    @Override
    public long decrBy(String key, long number) throws IOException {
        throw new UnsupportedOperationException("不支持此decrBy操作");
    }

    @Override
    public long incrBy(String key, long number) throws IOException {
        throw new UnsupportedOperationException("不支持此incrBy操作");
    }

    @Override
    public <T> T getrange(Class<T> tClass, String key, long startOffset, long endOffset) {
        throw new UnsupportedOperationException("不支持此getrange操作");
    }

    @Override
    public <T> T getSet(Class<T> tClass, String key, T value) throws IOException, ClassNotFoundException {
        return doGetSet(tClass, key, value);
    }

    @Override
    public <T> List<T> mget(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return doMget(tClass, keys);
    }

    @Override
    public boolean mset(Object... keysvalues) throws IOException {
        return doMset(keysvalues);
    }

    @Override
    public long strlen(String key) throws IOException {
        throw new UnsupportedOperationException("不支持此strlen操作");
    }
    /*===========================string end==========================================*/

    /*===========================list begin==========================================*/

    @Override
    public long rpush(String key, Object... value) throws IOException {
        return doRpush(key, value);
    }

    @Override
    public long lpush(String key, Object... value) throws IOException {
        return doLpush(key, value);
    }

    @Override
    public long llen(String key) throws IOException {
        return doLlen(key);
    }

    @Override
    public boolean lset(String key, int index, Object value) throws IOException {
        return doLset(key, index, value);
    }

    @Override
    public long linsert(String key, ListPosition where, Object pivot, Object value) throws IOException {
        return doLinsert(key, where, pivot, value);
    }

    @Override
    public <T> T lindex(Class<T> tClass, String key, long index) throws IOException, ClassNotFoundException {
        return doLindex(tClass, key, index);
    }

    @Override
    public <T> T lpop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doLpop(tClass, key);
    }

    @Override
    public <T> T rpop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        return doRpop(tClass, key);
    }

    @Override
    public <T> List<T> lrange(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        return doLrange(tClass, key, start, end);
    }

    @Override
    public long lrem(String key, int count, Object value) throws IOException {
        return doLrem(key, count, value);
    }

    @Override
    public boolean ltrim(String key, long start, long end) throws IOException {
        return doLtrim(key, start, end);
    }

    /*===========================list end============================================*/



//    private void delayTaskHandler(String key) {
//        JedisCommands jedis = null;
//        try {
//            jedis = getResource();
//            if (MAP_DELAY_EXECUTE.get(key) == null) {
//                synchronized (key.intern()) {
//                    if (MAP_DELAY_EXECUTE.get(key) == null) {
//                        MAP_DELAY_EXECUTE.put(key, true);
//                        while (true) {
//                            Map<String, Double> zSet = getZSet(key);
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
//
//    }
}
