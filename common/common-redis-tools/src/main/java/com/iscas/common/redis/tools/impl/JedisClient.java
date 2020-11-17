package com.iscas.common.redis.tools.impl;


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
    public long sadd(String key, Object... value) throws IOException {
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
    public <T> Set<T> sdiff(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
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
            Set<T> result = new HashSet<>();
            if (sdiffBytes != null) {
                for (byte[] sdiffByte : sdiffBytes) {
                    result.add((T) toObject(sdiffByte));
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
    public <T> Set<T> sinter(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
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
            Set<T> result = new HashSet<>();
            if (sinnerBytes != null) {
                for (byte[] sdiffByte : sinnerBytes) {
                    result.add((T) toObject(sdiffByte));
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
    public <T> Set<T> smembers(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        Set<T> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                value = new HashSet<>();
                Set<byte[]> set = jedisCommandsBytesSmembers(jedis, getBytesKey(key));
                for (byte[] bs : set){
                    value.add((T) toObject(bs));
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
    public <T> T spop(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byte[] result = jedis.spop(bytesKey);
                return result == null ? null : (T) toObject(result);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byte[] result = shardedJedis.spop(bytesKey);
                return result == null ? null : (T) toObject(result);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byte[] result = jedisCluster.spop(bytesKey);
                return result == null ? null : (T) toObject(result);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Set<T> spop(Class<T> tClass, String key, long count) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Set<byte[]> bytesResult = null;
            Set<T> result = null;
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
                    result.add((T) toObject(bytes));
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

    @Override
    public <T> Set<T> sunion(Class<T> tClass, String... keys) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[][] bytesKey = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytesKey[i] = getBytesKey(keys[i]);
            }
            Set<T> set = new HashSet<>();
            Set<byte[]> resultBytes = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                resultBytes = jedis.sunion(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sunion");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                resultBytes = jedisCluster.sunion(bytesKey);
            }
            if (resultBytes != null) {
                for (byte[] resultByte : resultBytes) {
                    set.add((T) toObject(resultByte));
                }
            }
            return set;
        } finally {
            returnResource(jc);
        }

    }

    /*========================================set end  ========================================================*/

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
    public <T> List<T> getList(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        List<T> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedisCommandsBytesExists(jedis, getBytesKey(key))) {
                List<byte[]> list = jedisCommandsBytesLrange(jedis, getBytesKey(key));
                value = new ArrayList<>();
                for (byte[] bs : list){
                    value.add((T) toObject(bs));
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
    public long setList(String key, List value, int cacheSeconds) throws IOException {
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
    public <T> T lpopList(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
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
        return (T) value;
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
    public <T> T rpopList(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
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
        return (T) value;
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


    /*===========================sort set begin========================================*/
    @Override
    public long zadd(String key, double score, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zadd(getBytesKey(key), score, toBytes(member));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zadd(getBytesKey(key), score, toBytes(member));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zadd(getBytesKey(key), score, toBytes(member));
            }
            return 0L;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zadd(String key, Map<? extends Object, Double> valueScoreMap, int cacheSeconds) throws IOException {
        long result = zadd(key, valueScoreMap);
        if (result > 0) {
            expire(key, cacheSeconds);
        }
        return result;
    }

    @Override
    public long zadd(String key, Map<? extends Object, Double> valueScoreMap) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Map<byte[], Double> members = new HashMap<>();
            for (Map.Entry<?, Double> entry : valueScoreMap.entrySet()) {
                members.put(toBytes(entry.getKey()), entry.getValue());
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zadd(getBytesKey(key), members);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zadd(getBytesKey(key), members);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zadd(getBytesKey(key), members);
            }
            return 0L;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zcard(String key) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zcard(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zcard(bytesKey);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zcard(bytesKey);
            }
            return 0L;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zcount(String key, double min, double max) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zcount(bytesKey, min, max);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zcount(bytesKey, min, max);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zcount(bytesKey, min, max);
            }
            return 0L;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public double zincrby(String key, double score, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zincrby(bytesKey, score, toBytes(member));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zincrby(bytesKey, score, toBytes(member));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zincrby(bytesKey, score, toBytes(member));
            }
            return 0L;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Set<T> zrange(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<byte[]> bytesResult = null;
            Set<T> result = new HashSet<>();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                bytesResult = jedis.zrange(bytesKey, start, end);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                bytesResult = shardedJedis.zrange(bytesKey, start, end);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                bytesResult = jedisCluster.zrange(bytesKey, start, end);
            }
            if (bytesResult != null) {
                for (byte[] bytes : bytesResult) {
                    result.add((T) toObject(bytes));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Map<T, Double> zrangeWithScoresToMap(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException {
        Set<Tuple> tuples = zrangeWithScores(key, start, end);
        Map<T, Double> result = new HashMap<>();
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                Object o = toObject(tuple.getBinaryElement());
                result.put((T) o, tuple.getScore());
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<Tuple> result = null;
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                result = jedis.zrangeWithScores(bytesKey, start, end);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                result = shardedJedis.zrangeWithScores(bytesKey, start, end);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                result = jedisCluster.zrangeWithScores(bytesKey, start, end);
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<T> result = new HashSet<>();
            Set<byte[]> bytes = null;
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                bytes = jedis.zrangeByScore(bytesKey, min, max);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                bytes = shardedJedis.zrangeByScore(bytesKey, min, max);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                bytes = jedisCluster.zrangeByScore(bytesKey, min, max);
            }
            if (bytes != null) {
                for (byte[] aByte : bytes) {
                    result.add((T) toObject(aByte));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<T> result = new HashSet<>();
            Set<byte[]> bytes = null;
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                bytes = jedis.zrangeByScore(bytesKey, min, max, offset, count);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                bytes = shardedJedis.zrangeByScore(bytesKey, min, max, offset, count);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                bytes = jedisCluster.zrangeByScore(bytesKey, min, max, offset, count);
            }
            if (bytes != null) {
                for (byte[] aByte : bytes) {
                    result.add((T) toObject(aByte));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<Tuple> tuples = null;
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                tuples = jedis.zrangeByScoreWithScores(bytesKey, min, max);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                tuples = shardedJedis.zrangeByScoreWithScores(bytesKey, min, max);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                tuples = jedisCluster.zrangeByScoreWithScores(bytesKey, min, max);
            }
            return tuples;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public  <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max)  throws IOException, ClassNotFoundException {
        Map<T, Double> result = new HashMap<>();
        Set<Tuple> tuples = zrangeByScoreWithScores(key, min, max);
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                result.put((T) toObject(tuple.getBinaryElement()), tuple.getScore());
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            Set<Tuple> tuples = null;
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                tuples = jedis.zrangeByScoreWithScores(bytesKey, min, max, offset, count);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                tuples = shardedJedis.zrangeByScoreWithScores(bytesKey, min, max, offset, count);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                tuples = jedisCluster.zrangeByScoreWithScores(bytesKey, min, max, offset, count);
            }
            return tuples;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException {
        Map<T, Double> result = new HashMap<>();
        Set<Tuple> tuples = zrangeByScoreWithScores(key, min, max, offset, count);
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                result.put((T) toObject(tuple.getBinaryElement()), tuple.getScore());
            }
        }
        return result;
    }

    @Override
    public long zrank(String key, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zrank(bytesKey, toBytes(member));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zrank(bytesKey, toBytes(member));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zrank(bytesKey, toBytes(member));
            }
            return -1;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zrevrank(String key, Object member) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zrevrank(bytesKey, toBytes(member));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zrevrank(bytesKey, toBytes(member));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zrevrank(bytesKey, toBytes(member));
            }
            return -1;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zrem(String key, Object... members) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[][] memberBytes = new byte[members.length][];
            for (int i = 0; i < members.length; i++) {
                memberBytes[i] = toBytes(members[i]);
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zrem(bytesKey, memberBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zrem(bytesKey, memberBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zrem(bytesKey, memberBytes);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zremrangeByRank(String key, int start, int end) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zremrangeByRank(bytesKey, start, end);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zremrangeByRank(bytesKey, start, end);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zremrangeByRank(bytesKey, start, end);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zremrangeByScore(String key, double min, double max) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zremrangeByScore(bytesKey, min, max);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zremrangeByScore(bytesKey, min, max);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zremrangeByScore(bytesKey, min, max);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Double zscore(String key, Object memeber) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zscore(bytesKey, toBytes(memeber));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.zscore(bytesKey, toBytes(memeber));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zscore(bytesKey, toBytes(memeber));
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zinterstore(String dstKey, String... keys) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] dstBytesKey = getBytesKey(dstKey);
            byte[][] bytesKey = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytesKey[i] = getBytesKey(keys[i]);
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zinterstore(dstBytesKey, bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持zinterstore");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zinterstore(dstBytesKey, bytesKey);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zunionstore(String dstKey, String... keys) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] dstBytesKey = getBytesKey(dstKey);
            byte[][] bytesKey = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytesKey[i] = getBytesKey(keys[i]);
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.zunionstore(dstBytesKey, bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持zinterstore");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.zunionstore(dstBytesKey, bytesKey);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    /*===========================sort set end==========================================*/


    /*===========================hash begin==========================================*/

    @Override
    public boolean hmset(String key, Map map, int cacheSenconds) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Map<byte[], byte[]> bytesMap = new HashMap<>();
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                bytesMap.put(toBytes(entry.getKey()), toBytes(entry.getValue()));
            }
            String result = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                result = jedis.hmset(bytesKey, bytesMap);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                result = shardedJedis.hmset(bytesKey, bytesMap);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                result = jedisCluster.hmset(bytesKey, bytesMap);
            }
            if ("ok".equalsIgnoreCase(result)) {
                if (!Objects.equals(0, cacheSenconds)) {
                    expire(key, cacheSenconds * 1000);
                }
                return true;
            }
            return false;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <K extends Object, V extends Object> Map<K, V> hgetAll(Class<K> keyClass, Class<V> valClass, String key) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Map<byte[], byte[]> result = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                result = jedis.hgetAll(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                result = shardedJedis.hgetAll(bytesKey);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                result = jedisCluster.hgetAll(bytesKey);
            }
            Map<K, V> mapRes = new HashMap<>();
            if (result != null) {
                for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                    mapRes.put((K) toObject(entry.getKey()), (V) toObject(entry.getValue()));
                }
            }
            return mapRes;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long hdel(String key, Object... fields) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[][] fieldsBytes = new byte[fields.length][];
            for (int i = 0; i < fields.length; i++) {
                fieldsBytes[i] = toBytes(fields[i]);
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.hdel(bytesKey, fieldsBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.hdel(bytesKey, fieldsBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.hdel(bytesKey, fieldsBytes);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public boolean hexists(String key, Object field) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.hexists(bytesKey, toBytes(field));
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.hexists(bytesKey, toBytes(field));
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.hexists(bytesKey, toBytes(field));
            }
            return false;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> T hget(Class<T> tClass, String key, String field) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[] fieldBytes = toBytes(field);
            byte[] byteResult = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byteResult = jedis.hget(bytesKey, fieldBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byteResult = shardedJedis.hget(bytesKey, fieldBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byteResult = jedisCluster.hget(bytesKey, fieldBytes);
            }
            if (byteResult != null) {
                return (T) toObject(byteResult);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long hset(String key, Object field, Object value) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[] fieldBytes = toBytes(field);
            byte[] valueBytes = toBytes(value);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.hset(bytesKey, fieldBytes, valueBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.hset(bytesKey, fieldBytes, valueBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.hset(bytesKey, fieldBytes, valueBytes);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long hsetnx(String key, Object field, Object value) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            byte[] fieldBytes = toBytes(field);
            byte[] valueBytes = toBytes(value);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.hsetnx(bytesKey, fieldBytes, valueBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.hsetnx(bytesKey, fieldBytes, valueBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.hsetnx(bytesKey, fieldBytes, valueBytes);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> List<T> hvals(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Collection<byte[]> byteResult = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byteResult =  jedis.hvals(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byteResult =  shardedJedis.hvals(bytesKey);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byteResult =  jedisCluster.hvals(bytesKey);
            }
            List<T> result = new ArrayList<>();
            if (byteResult != null) {
                for (byte[] bytes : byteResult) {
                    result.add((T) toObject(bytes));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long hincrby(String key, String field, long value) throws IOException {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public Double hincrby(String key, String field, double value) throws IOException {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public <T> Set<T> hkeys(Class<T> tClass, String key) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            Set<byte[]> byteResult = null;
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byteResult = jedis.hkeys(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byteResult = shardedJedis.hkeys(bytesKey);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byteResult = jedisCluster.hkeys(bytesKey);
            }
            Set<T> result = new HashSet<>();
            if (byteResult != null) {
                for (byte[] bytes : byteResult) {
                    result.add((T) toObject(bytes));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long hlen(String key) throws IOException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.hlen(bytesKey);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                return shardedJedis.hlen(bytesKey);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.hlen(bytesKey);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public <T> List<T> hmget(Class<T> tClass, String key, Object... fields) throws IOException, ClassNotFoundException {
        JedisCommands jc = null;
        try {
            jc = getResource();
            byte[] bytesKey = getBytesKey(key);
            List<byte[]> byteResult = null;
            byte[][] fieldBytes = new byte[fields.length][];
            for (int i = 0; i < fields.length; i++) {
                fieldBytes[i] = toBytes(fields[i]);
            }
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                byteResult = jedis.hmget(bytesKey, fieldBytes);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                byteResult = shardedJedis.hmget(bytesKey, fieldBytes);
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                byteResult = jedisCluster.hmget(bytesKey, fieldBytes);
            }
            List<T> result = new ArrayList<>();
            if (byteResult != null) {
                for (byte[] bytes : byteResult) {
                    result.add((T) toObject(bytes));
                }
            }
            return result;
        } finally {
            returnResource(jc);
        }
    }

    /*===========================hash end==========================================*/



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
