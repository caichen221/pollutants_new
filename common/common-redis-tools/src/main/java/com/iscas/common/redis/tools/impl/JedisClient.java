package com.iscas.common.redis.tools.impl;


import com.alibaba.fastjson.JSONObject;
import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.IJedisClient;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.helper.MyStringHelper;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * JedisClient
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/5 14:46
 * @since jdk1.8
 */
public class JedisClient implements IJedisClient {

    private static String RESULT_OK = "OK";
    private static int RESULT_1 = 1;
    private static String DELAY_QUEUE_DEFUALT_KEY = "delay_queue_default_key_20190806";
    private static Map<String, Consumer> MAP_DELAY = new ConcurrentHashMap<>();
    private static Map<String, Boolean> MAP_DELAY_EXECUTE = new ConcurrentHashMap<>();

    private Object jedisPool;
    public JedisClient(JedisConnection jedisConnection, ConfigInfo configInfo) {
        jedisConnection.initConfig(configInfo);
        jedisPool = jedisConnection.getPool();
    }

    /**
     * 获取数据，获取对象数据，需经过反序列化
     * @param key 键
     * @return 值
     */
    @Override
    public <T> T get(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        Object value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                value = toObject(jedisCommandsBytesGet(jedis, getBytesKey(key)));
            }
        } finally {
            returnResource(jedis);
        }
        return (T) value;
    }

    /**
     * 设置数据，对象数据，序列化后存入redis
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  boolean set(String key, Object value, int cacheSeconds) throws IOException {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedisCommandsBytesSet(jedis, getBytesKey(key), toBytes(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
    }

    /**
     * 获取List数据，List中为对象，经过反序列化
     * @param key 键
     * @return 值
     */
    @Override
    public List<Object> getList(String key) throws IOException, ClassNotFoundException {
        List<Object> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                List<byte[]> list = jedisCommandsBytesLrange(jedis, getBytesKey(key));
                value = new ArrayList<>();
                for (byte[] bs : list){
                    value.add(toObject(bs));
                }
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List数据，List中数据为对象，经过序列化后存储
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  long setList(String key, List<Object> value, int cacheSeconds) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                jedis.del(key);
            }
            if (value == null || value.size() == 0) {
                throw new RuntimeException("不能传入空集合");
            }
            byte[][] list = new byte[value.size()][];
            for (int i = 0; i< value.size(); i++){
                list[i] = toBytes(value.get(i));
            }
            result = jedisCommandsBytesRpush(jedis, getBytesKey(key), list);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }

        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List中添加值，添加的值为对象
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public long listAdd(String key, Object... value) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (value == null || value.length == 0) {
                throw new RuntimeException("不能传入空集合");
            }
            byte[][] list = new byte[value.length][];
            for (int i = 0; i< value.length; i++){
                list[i] = toBytes(value[i]);
            }
            result = jedisCommandsBytesRpush(jedis, getBytesKey(key), list);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 从左边pop数据，适用于队列，pop出对象
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param key
     * @throws
     * @return java.lang.Object
     */
    @Override
    public Object lpopList(String key) throws IOException, ClassNotFoundException {
        Object value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                byte[] data = jedisCommandsBytesLpop(jedis, getBytesKey(key));
                value = toObject(data);
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 从右边pop数据，适用于栈，pop出对象
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param key
     * @throws
     * @return java.lang.Object
     */
    @Override
    public Object rpopList(String key) throws IOException, ClassNotFoundException {
        Object value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                byte[] data = jedisCommandsBytesRpop(jedis, getBytesKey(key));
                value = toObject(data);
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 为了使函数名与命令更接近，弃用，见新函数{@link #smembers(String)}
     * 获取集合数据，类型为对象
     * @param key 键
     * @return 值
     */
    @Override
    public Set<Object> getSet(String key) throws IOException, ClassNotFoundException {
        return smembers(key);
    }

    /**
     * 设置Set，值为任意对象类型
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  long setSet(String key, Set<Object> value, int cacheSeconds) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                jedis.del(key);
            }
            byte[][] bytes = new byte[value.size()][];
            int i = 0;
            for (Object obj: value) {
                bytes[i++] = toBytes(obj);
            }
            result = jedisCommandsBytesSadd(jedis, getBytesKey(key), bytes);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set中追加值，类型为对象
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public  long setSetAdd(String key, Object... value) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            byte[][] bytes = new byte[value.length][];
            int i = 0;
            for (Object obj: value) {
                bytes[i++] = toBytes(obj);
            }
            result = jedisCommandsBytesSadd(jedis, getBytesKey(key), bytes);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public Map<Object, Double> getZSet(String key) throws IOException, ClassNotFoundException {
        Map<Object, Double> result = new LinkedHashMap<>();
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                Set<Tuple> tuples = jedis.zrangeByScoreWithScores(key, 0, -1);
                if (tuples != null) {
                    for (Tuple tuple : tuples) {
                        result.put(JSONObject.parse(tuple.getBinaryElement()), tuple.getScore());
                    }
                }
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long setZSet(String key, Map<Object, Double> valueScoreMap, int cacheSeconds) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (valueScoreMap == null || valueScoreMap.size() == 0 ) {
                throw new RuntimeException("集合不能为空");
            }
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                jedis.del(key);
            }
            //暂时先将Object 转为String
            if (valueScoreMap != null) {
                Map<String, Double> strValueScoreMap = new HashMap<>();

                for (Map.Entry<Object, Double> entry : valueScoreMap.entrySet()) {
                    Object key1 = entry.getKey();
                    Double value = entry.getValue();
                    String s = JSONObject.toJSONString(key1);
                    strValueScoreMap.put(s, value);
                }
                result = jedis.zadd(key, strValueScoreMap);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }


            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long setZSetAdd(String key, Map<Object, Double> valueScoreMap) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            //暂时先将Object 转为String
            if (valueScoreMap != null) {
                Map<String, Double> strValueScoreMap = new HashMap<>();
                for (Map.Entry<Object, Double> entry : valueScoreMap.entrySet()) {
                    Object key1 = entry.getKey();
                    Double value = entry.getValue();
                    String s = JSONObject.toJSONString(key1);
                    strValueScoreMap.put(s, value);
                }
                result = jedis.zadd(key, strValueScoreMap);

            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public Map<byte[], byte[]> getBytesMap(byte[] key) {
        Map<byte[], byte[]> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis instanceof Jedis) {
                Jedis jd = (Jedis) jedis;
                if (jd.exists(key)) {
                    value = jd.hgetAll(key);
                }
            }

        } finally {
            returnResource(jedis);
        }
        return value;

    }

    /**
     * 获取Map 类型为对象
     * @param key 键
     * @return 值
     */
    @Override
    public Map<String, Object> getMap(String key) throws IOException, ClassNotFoundException {
        Map<String, Object> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                value = new HashMap<>();
                Map<byte[], byte[]> map = jedisCommandsBytesHgetall(jedis, getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()){
                    value.put(MyStringHelper.toString(e.getKey()), toObject(e.getValue()));
                }
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }


    public boolean setMap(String key, String field, String value, int cacheSeconds) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
//            if (jedis.exists(key)) {
//                jedis.del(key);
//            }
            result = jedis.hset(key, field, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return result == 1;
    }

    @Override
    public boolean setBytesMap(byte[] key, Map<byte[], byte[]> value, int cacheSeconds) {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis instanceof Jedis) {
                Jedis jd = (Jedis) jedis;
                if (jd.exists(key)){
                    jd.del(key);
                }
                result = jd.hmset(key, value);
                if (cacheSeconds != 0) {
                    jd.expire(key, cacheSeconds);
                }
            }
//            if (jedis.exists(key)) {
//                jedis.del(key);
//            }
//            result = jedis.hmset(key, value);
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
    }

    /**
     * 设置Map 类型为对象
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  boolean setMap(String key, Map<String, Object> value, int cacheSeconds) throws IOException {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = new HashMap<>();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedisCommandsBytesHmset(jedis, getBytesKey(key), (Map<byte[], byte[]>)map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
    }

    /**
     * 向Map中添加值， 类型为对象
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public  boolean mapPut(String key, Map<String, Object> value) throws IOException {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> map = new HashMap<>();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedisCommandsBytesHmset(jedis, getBytesKey(key), (Map<byte[], byte[]>)map);
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
    }

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    @Override
    public  long mapRemove(String key, String mapKey) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedisCommandsBytesHdel(jedis, getBytesKey(key), getBytesKey(mapKey));
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    @Override
    public  boolean mapExists(String key, String mapKey) throws IOException {
        boolean result = false;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedisCommandsBytesHexists(jedis, getBytesKey(key), getBytesKey(mapKey));
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    @Override
    public  long del(String key) throws IOException {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))){
                result = jedisCommandsBytesDel(jedis, getBytesKey(key));
            }else{
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    @Override
    public  boolean exists(String key) throws IOException {
        boolean result = false;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedisCommandsBytesExists(jedis, getBytesKey(key));
        } finally {
            returnResource(jedis);
        }
        return result;

    }


    /**
     * 获取资源
     * @return
     * @throws JedisException
     */
    public JedisCommands getResource() throws JedisException {
        JedisCommands jedis = null;
        try {
            if (jedisPool instanceof Pool) {
                jedis = (JedisCommands) ((Pool)jedisPool).getResource();
            } else if (jedisPool instanceof JedisCluster){
                jedis = (JedisCluster) jedisPool;
            } else if (jedisPool instanceof ShardedJedisPool) {
                jedis = ((ShardedJedisPool) jedisPool).getResource();
            }
        } catch (JedisException e) {
            returnBrokenResource(jedis);
            throw e;
        }
        return jedis;
    }

    /**
     * 归还资源
     * @param jedis
     * @param jedis
     */
    public  void returnBrokenResource(JedisCommands jedis) {
        if (jedis != null) {
            if (jedis instanceof  Jedis) {
                Jedis jedis1 = (Jedis) jedis;
                if (jedis1.isConnected()) {
                    jedis1.close();
                }
            } else if (jedis instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jedis;
                shardedJedis.close();
            } else if (jedis instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jedis;
            }

        }
    }

    /**
     * 释放资源
     * @param jedis
     */
    public  void returnResource(JedisCommands jedis) {
        if (jedis != null ) {
            if (jedis instanceof  Jedis) {
                Jedis jedis1 = (Jedis) jedis;
                if (jedis1.isConnected()) {
                    jedis1.close();
                }
            } else if (jedis instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jedis;
                shardedJedis.close();
            } else if (jedis instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jedis;
                //集群不需要手动释放连接
            }
        }
    }

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
     * @date 2018/11/6
     * @param lockName 锁名称
     * @param lockTimeoutInMS 锁超时时间
     * @throws
     * @return java.lang.String 锁标识
     */
    @Override
    public String acquireLock(String lockName, long lockTimeoutInMS) {
        String retIdentifier = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            String identifier = UUID.randomUUID().toString();
            String lockKey = "lock:" + lockName;
            int lockExpire = (int) (lockTimeoutInMS / 1000);
            String result = jedis.set(lockKey, identifier, "NX", "PX", lockExpire);
            if (RESULT_OK.equals(result)) {
                retIdentifier = identifier;
            }
        } finally {
            returnResource(jedis);
        }
        return retIdentifier;
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
    @Override
    public boolean releaseLock(String lockName, String identifier) {
        JedisCommands conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            if (identifier == null) {
                return true;
            }
            conn = getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedisCommandsBytesLuaEvalSha(conn, script, Collections.singletonList(lockKey), Collections.singletonList(identifier));
            if (Objects.equals(RESULT_1, result)) {
                retFlag = true;
            }

        }  finally {
            returnResource(conn);
        }
        return retFlag;
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
    @Override
    public boolean accessLimit(String ip, int timeout, int limit) {
        JedisCommands conn = null;
        try {
            conn = getResource();
            String lua = "local num = redis.call('incr', KEYS[1])\n" +
                    "if tonumber(num) == 1 then\n" +
                    "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                    "\treturn 1\n" +
                    "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                    "\treturn 0\n" +
                    "else \n" +
                    "\treturn 1\n" +
                    "end\n";
            Object result = jedisCommandsBytesLuaEvalSha(conn, lua, Arrays.asList(ip), Arrays.asList(String.valueOf(timeout),
                    String.valueOf(limit)));
            return "1".equals(result == null ? null : result.toString());
        }  finally {
            returnResource(conn);
        }

    }

    private boolean jedisCommandsBytesExists(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.exists(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.exists(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.exists(bytesKey);
        }
        return false;
    }

    private byte[] jedisCommandsBytesGet(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.get(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.get(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.get(bytesKey);
        }
        return null;
    }

    private String jedisCommandsBytesSet(JedisCommands jedisCommands, byte[] bytesKey, byte[] bytesValue) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.set(bytesKey, bytesValue);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.set(bytesKey, bytesValue);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.set(bytesKey, bytesValue);
        }
        return null;
    }

    private List<byte[]> jedisCommandsBytesLrange(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.lrange(bytesKey, 0, -1);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.lrange(bytesKey, 0, -1);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.lrange(bytesKey, 0, -1);
        }
        return null;
    }

    private long jedisCommandsBytesRpush(JedisCommands jedisCommands, byte[] bytesKey, byte[][] value) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.rpush(bytesKey, value);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.rpush(bytesKey, value);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.rpush(bytesKey, value);
        }
        return 0;
    }

    private byte[] jedisCommandsBytesLpop(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.lpop(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.lpop(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.lpop(bytesKey);
        }
        return null;
    }
    private byte[] jedisCommandsBytesRpop(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.rpop(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.rpop(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.rpop(bytesKey);
        }
        return null;
    }
    private Set<byte[]> jedisCommandsBytesSmembers(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.smembers(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.smembers(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.smembers(bytesKey);
        }
        return null;
    }

    private long  jedisCommandsBytesSadd(JedisCommands jedisCommands, byte[] bytesKey, byte[][] bytesValue) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.sadd(bytesKey, bytesValue);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.sadd(bytesKey, bytesValue);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
           return jedisCluster.sadd(bytesKey, bytesValue);
        }
        return 0;
    }

    private Map<byte[], byte[]> jedisCommandsBytesHgetall(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.hgetAll(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.hgetAll(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.hgetAll(bytesKey);
        }
        return null;
    }

    private String jedisCommandsBytesHmset(JedisCommands jedisCommands, byte[] bytesKey, Map<byte[], byte[]> bytesValue) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.hmset(bytesKey, bytesValue);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.hmset(bytesKey, bytesValue);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.hmset(bytesKey, bytesValue);
        }
        return null;
    }

    private long  jedisCommandsBytesHdel(JedisCommands jedisCommands, byte[] bytesKey, byte[] bytesValue) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.hdel(bytesKey, bytesValue);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.hdel(bytesKey, bytesValue);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.hdel(bytesKey, bytesValue);
        }
        return 0;
    }

    private boolean  jedisCommandsBytesHexists(JedisCommands jedisCommands, byte[] bytesKey, byte[] field) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.hexists(bytesKey, field);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.hexists(bytesKey, field);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            jedisCluster.hexists(bytesKey, field);
        }
        return false;
    }

    private long  jedisCommandsBytesDel(JedisCommands jedisCommands, byte[] bytesKey) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.del(bytesKey);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            return shardedJedis.del(bytesKey);
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            return jedisCluster.del(bytesKey);
        }
        return 0;
    }

    private Object jedisCommandsBytesLuaEvalSha(JedisCommands jedisCommands, String lua, List key, List val) {
        if (jedisCommands instanceof Jedis) {
            Jedis jedis = (Jedis) jedisCommands;
            return jedis.evalsha(jedis.scriptLoad(lua), key, val);
        } else if (jedisCommands instanceof ShardedJedis) {
            ShardedJedis shardedJedis = (ShardedJedis) jedisCommands;
            throw new RuntimeException("ShardedJedis 暂不支持执行Lua脚本");
        } else if (jedisCommands instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) jedisCommands;
            return jedisCluster.evalsha(jedisCluster.scriptLoad(lua, lua), key, val);
        }
        return 0;
    }

    @Override
    public void deleteByPattern(String pattern) throws UnsupportedEncodingException {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis instanceof Jedis) {
                Jedis jd = (Jedis) jedis;
                Set<String> keys = jd.keys(pattern);
                if(keys != null && !keys.isEmpty()) {
                    String[] keyArr = new String[keys.size()];
                    jd.del(keys.toArray(keyArr));
                }
            } else if (jedis instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jedis;
                throw new RuntimeException("ShardedJedis 暂不支持pattern删除");
            } else if (jedis instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jedis;
                throw new RuntimeException("JedisCluster 暂不支持pattern删除");
            }
        } finally {
            returnResource(jedis);
        }

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
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (milliseconds <= 0) {
                return;
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                jedis.pexpire(getBytesKey(key), milliseconds);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                shardedJedis.pexpire(getBytesKey(key), milliseconds);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                jedisCluster.pexpire(getBytesKey(key), milliseconds);
            }
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long scard(String key) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.scard(getBytesKey(key));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.scard(getBytesKey(key));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.scard(getBytesKey(key));
            }
            return 0;
        }finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<Object> sdiff(String... keys) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            //将key转为byte，与存入时对应
            byte[][] byteKeys = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                byteKeys[i] = getBytesKey(keys[i]);
            }
            Set<byte[]> sdiffBytes = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                sdiffBytes = jedis.sdiff(byteKeys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sdiff");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                sdiffBytes = jedisCluster.sdiff(byteKeys);
            }
            //转化结果
            Set<Object> result = new HashSet<>();
            if (sdiffBytes != null) {
                for (byte[] sdiffByte : sdiffBytes) {
                    result.add(toObject(sdiffByte));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long sdiffStore(String newkey, String... keys) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            //将key转为byte，与存入时对应
            byte[][] byteKeys = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                byteKeys[i] = getBytesKey(keys[i]);
            }
            byte[] bytesNewKey = getBytesKey(newkey);
            Set<byte[]> sdiffBytes = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sdiffstore(bytesNewKey, byteKeys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sdiff");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sdiffstore(bytesNewKey, byteKeys);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<Object> sinter(String... keys) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            //将key转为byte，与存入时对应
            byte[][] byteKeys = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                byteKeys[i] = getBytesKey(keys[i]);
            }
            Set<byte[]> sinnerBytes = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                sinnerBytes = jedis.sinter(byteKeys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sinter");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                sinnerBytes = jedisCluster.sinter(byteKeys);
            }
            //转化结果
            Set<Object> result = new HashSet<>();
            if (sinnerBytes != null) {
                for (byte[] sdiffByte : sinnerBytes) {
                    result.add(toObject(sdiffByte));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long sinterStore(String newKey, String... keys) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            //将key转为byte，与存入时对应
            byte[][] byteKeys = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                byteKeys[i] = getBytesKey(keys[i]);
            }
            byte[] bytesNewKey = getBytesKey(newKey);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sinterstore(bytesNewKey, byteKeys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sinter");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sinterstore(bytesNewKey, byteKeys);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public boolean sismember(String key, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[] bytes = toBytes(member);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sismember(bytesKey, bytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.sismember(bytesKey, bytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sismember(bytesKey, bytes);
            }
            throw new RuntimeException("不支持的JedisCommands类型:" + jc);
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<Object> smembers(String key) throws IOException, ClassNotFoundException {
        Set<Object> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                value = new HashSet<>();
                Set<byte[]> set = jedisCommandsBytesSmembers(jedis, getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs));
                }
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    @Override
    public long smove(String srckey, String dstkey, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            byte[] srcKeyBytes = getBytesKey(srckey);
            byte[] dstKeyBytes = getBytesKey(dstkey);
            byte[] memberBytes = toBytes(member);
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.smove(srcKeyBytes, dstKeyBytes, memberBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持smove");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.smove(srcKeyBytes, dstKeyBytes, memberBytes);
            }
            return -1;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Object spop(String key) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byte[] result = jedis.spop(bytesKey);
                return result == null ? null : toObject(result);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byte[] result = shardedJedis.spop(bytesKey);
                return result == null ? null : toObject(result);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byte[] result = jedisCluster.spop(bytesKey);
                return result == null ? null : toObject(result);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<Object> spop(String key, long count) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Set<byte[]> bytesResult = null;
            Set<Object> result = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                bytesResult = jedis.spop(bytesKey, count);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                bytesResult = shardedJedis.spop(bytesKey, count);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                bytesResult = jedisCluster.spop(bytesKey, count);
            }
            if (bytesResult != null) {
                result = new HashSet<>();
                for (byte[] bytes : bytesResult) {
                    result.add(toObject(bytes));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long srem(String key, Object... member) throws IOException {
        JedisCommands jc = null;
        try {
            byte[] keyBytes = getBytesKey(key);
            byte[][] memberBytes = new byte[member.length][];
            for (int i = 0; i < member.length; i++) {
                byte[] oBytes = toBytes(member[i]);
                memberBytes[i] = oBytes;
            }

            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.srem(keyBytes, memberBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.srem(keyBytes, memberBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.srem(keyBytes, memberBytes);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }


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
