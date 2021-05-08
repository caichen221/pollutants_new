package com.iscas.common.redis.tools.impl;


import com.iscas.common.redis.tools.IJedisClient;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class JdkNoneRedisClient extends JdkCommonClient implements IJedisClient {

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
    public long del(String key) throws IOException {
        return 0L;
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    @Override
    public  boolean exists(String key) throws IOException {
        return false;

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

    @Override
    public void deleteByPattern(String pattern) throws UnsupportedEncodingException {


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
       return 0L;
    }

    /**
     * 向Set中追加值，类型为对象
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public long sadd(String key, Object... value) throws IOException {
        return 0L;
    }

    @Override
    public long scard(String key) throws IOException {
       return 0L;
    }

    @Override
    public <T> Set<T> sdiff(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public long sdiffStore(String newkey, String... keys) throws IOException, ClassNotFoundException {
        return 0L;
    }

    @Override
    public <T> Set<T> sinter(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public long sinterStore(String newKey, String... keys) throws IOException {
        return 0L;
    }

    @Override
    public boolean sismember(String key, Object member) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Set<T> smembers(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long smove(String srckey, String dstkey, Object member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T spop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Set<T> spop(Class<T> tClass, String key, long count) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long srem(String key, Object... member) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> Set<T> sunion(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();


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
    public boolean hmset(String key, Map map, int cacheSenconds) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <K extends Object, V extends Object> Map<K, V> hgetAll(Class<K> keyClass, Class<V> valClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long hdel(String key, Object... fields) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean hexists(String key, Object field) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T hget(Class<T> tClass, String key, String field) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long hset(String key, Object field, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long hsetnx(String key, Object field, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> List<T> hvals(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long hincrby(String key, String field, long value) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public Double hincrby(String key, String field, double value) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public <T> Set<T> hkeys(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long hlen(String key) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> List<T> hmget(Class<T> tClass, String key, Object... fields) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

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
    public  boolean set(String key, Object value, int cacheSeconds) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T get(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long setnx(String key, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long setrange(String key, long offset, Object value) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此setrange操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public long append(String key, String value) {
        throw new UnsupportedOperationException("redis暂不支持此append操作,请使用IJedisStrClient中对应的函数");

    }

    @Override
    public long decrBy(String key, long number) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此decrBy操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public long incrBy(String key, long number) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此incrBy操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public <T> T getrange(Class<T> tClass, String key, long startOffset, long endOffset) {
        throw new UnsupportedOperationException("redis暂不支持此getrange操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public <T> T getSet(Class<T> tClass, String key, String value) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> List<T> mget(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean mset(Object... keysvalues) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long strlen(String key) throws IOException {
        throw new UnsupportedOperationException("redis暂不支持此strlen操作,请使用IJedisStrClient中对应的函数");
    }

    @Override
    public long rpush(String key, Object... value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long lpush(String key, String... value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long llen(String key) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean lset(String key, int index, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long linsert(String key, ListPosition where, Object pivot, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T lindex(Class<T> tClass, String key, long index) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T lpop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> T rpop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> List<T> lrange(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();

    }

    @Override
    public long lrem(String key, int count, Object value) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean ltrim(String key, long start, long end) throws IOException {
        throw new UnsupportedOperationException();

    }

    /*===========================string end============================================*/



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
