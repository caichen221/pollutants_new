package com.iscas.common.redis.tools.impl;


import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.IJedisStrClient;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.helper.MyStringHelper;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * JedisClient
 *
 * 将原来的{@link IJedisStrClient} 中<br/>
 * 存储字符串的方式单独拆分出来，以免造成对象存储与字符串存储调用时混淆
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/15
 * @since jdk1.8
 */
public class JedisStrClient implements IJedisStrClient {

    private static String RESULT_OK = "OK";
    private static int RESULT_1 = 1;
    private static String DELAY_QUEUE_DEFUALT_KEY = "delay_queue_default_key_20190806";
    private static Map<String, Consumer> MAP_DELAY = new ConcurrentHashMap<>();
    private static Map<String, Boolean> MAP_DELAY_EXECUTE = new ConcurrentHashMap<>();

    private Object jedisPool;
    public JedisStrClient(JedisConnection jedisConnection, ConfigInfo configInfo) {
        jedisConnection.initConfig(configInfo);
        jedisPool = jedisConnection.getPool();
    }

    /*=============================set begin===============================================*/
    /**
     * 设置Set, 值为字符串类型
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  long sadd(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (value == null || value.size() == 0 ) {
                throw new RuntimeException("集合不能为空");
            }
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            String[] strs = new String[value.size()];
            int i = 0;
            for (String str: value) {
                strs[i++] = str;
            }
            result = jedis.sadd(key, strs);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set中追加值，值为字符串
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public  long sadd(String key, String... value) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.sadd(key, value);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long scard(String key) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.scard(key);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<String> sdiff(String... keys) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sdiff(keys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sdiff");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sdiff(keys);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long sdiffStore(String newkey, String... keys) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sdiffstore(newkey, keys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sdiff");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sdiffstore(newkey, keys);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<String> sinter(String... keys) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sinter(keys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sinter");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sinter(keys);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long sinterStore(String newKey, String... keys) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sinterstore(newKey, keys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sinter");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sinterstore(newKey, keys);
            }
            return 0;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public boolean sismember(String key, String member) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            return jc.sismember(key, member);
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        Set<String> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.smembers(key);
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    @Override
    public long smove(String srckey, String dstkey, String member) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.smove(srckey, dstkey, member);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持smove");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.smove(srckey, dstkey, member);
            }
            return -1;
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public String spop(String key) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            return jc.spop(key);
        } finally {
            returnResource(jc);
        }

    }

    @Override
    public Set<String> spop(String key, long count) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            return jc.spop(key, count);
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long srem(String key, String... member) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            return jc.srem(key, member);
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public Set<String> sunion(String... keys) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            if (jc instanceof Jedis) {
                Jedis jedis = (Jedis) jc;
                return jedis.sunion(keys);
            } else if (jc instanceof ShardedJedis) {
                ShardedJedis shardedJedis = (ShardedJedis) jc;
                throw new RuntimeException("ShardedJedis 暂不支持sunion");
            } else if (jc instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) jc;
                return jedisCluster.sunion(keys);
            }
            return null;
        } finally {
            returnResource(jc);
        }
    }

    /*=============================set end  ===============================================*/

    /**
     * 获取数据，获取字符串数据
     * @param key 键
     * @return 值
     */
    @Override
    public String get(String key) {
        String value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = MyStringHelper.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        }finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置数据，字符串数据
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  boolean set(String key, String value, int cacheSeconds) {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
    }

    /**
     * 获取List数据，List中数据为字符串
     * @param key 键
     * @return 值
     */
    @Override
    public List<String> getList(String key) {
        List<String> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
            }
        }  finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List数据，List中值为字符串
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[])value.toArray(new String[0]));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List中添加值，添加的值为字符串
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public  long listAdd(String key, String... value) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.rpush(key, value);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     *  从左边pop数据，适用于队列
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param key
     * @throws
     * @return java.lang.String
     */
    @Override
    public String lpopList(String key) {
        String value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lpop(key);
            }
        }  finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     *  从右边pop数据，适用于栈
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param key
     * @throws
     * @return java.lang.String
     */
    @Override
    public String rpopList(String key) {
        String value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.rpop(key);
            }
        }  finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Map
     * @param key 键
     * @return 值
     */
    @Override
    public String getMapByMapKey(String key, String mapKey) {
        String value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.hget(key, mapKey);
            }
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Map
     * @param key 键
     * @return 值
     */
    @Override
    public Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.hgetAll(key);
            }
        } finally {
            returnResource(jedis);
        }
        return value;
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

    public  boolean setMap(String key, String field, String value, int cacheSeconds) {
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
     * 设置Map, 类型为字符串
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public  boolean setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return "OK".equals(result);
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
     * 向Map中添加值 类型为字符串
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public  boolean mapPut(String key, Map<String, String> value) {
        String result = null;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.hmset(key, value);
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
    public  long mapRemove(String key, String mapKey) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(key, mapKey);
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
    public  boolean mapExists(String key, String mapKey) {
        boolean result = false;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(key, mapKey);
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
    public  long del(String key) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)){
                result = jedis.del(key);
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
    public  boolean exists(String key) {
        boolean result = false;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(key);
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

    @Override
    public void putDelayQueue(String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
        //使用默认key
        String hostAddress = null;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        putDelayQueue(DELAY_QUEUE_DEFUALT_KEY.concat(hostAddress), task, timeout, timeUnit, consumer);
    }

    @Override
    public void putDelayQueue(String key, String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer) {
        long l = System.currentTimeMillis();
        long x = timeUnit.toMillis(timeout);
        long targetScore = l + x;
        Map<String, Double> map = new HashMap();
        map.put(task, Double.valueOf(String.valueOf(targetScore)));
        zadd(key, map);
        MAP_DELAY.put(task, consumer);
        delayTaskHandler(key);
    }

    @Override
    public void expire(String key, long milliseconds) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (milliseconds <= 0) {
                return;
            }
            jedis.pexpire(key, milliseconds);
        } finally {
            returnResource(jedis);
        }
    }

    private void delayTaskHandler(String key) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (MAP_DELAY_EXECUTE.get(key) == null) {
                synchronized (key.intern()) {
                    if (MAP_DELAY_EXECUTE.get(key) == null) {
                        MAP_DELAY_EXECUTE.put(key, true);
                        while (true) {
                            Map<String, Double> zSet = zrangeWithScoresToMap(key, 0, -1);
                            if (zSet == null || zSet.size() == 0) {
                                break;
                            }
                            for (Map.Entry<String, Double> entry : zSet.entrySet()) {
                                String storeTask = entry.getKey();
                                Double score = entry.getValue();
                                if (System.currentTimeMillis() - score > 0) {
                                    //开始执行任务
                                    MAP_DELAY.get(storeTask).accept(storeTask);
                                    //删除这个值
                                    String script = "return redis.call('zrem', KEYS[1], ARGV[1])";
                                    jedisCommandsBytesLuaEvalSha(jedis, script, Collections.singletonList(key), Collections.singletonList(storeTask));
                                }
                            }
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } finally {
            returnResource(jedis);
        }

    }

    /*==============================sort set begin=====================================================*/
    @Override
    public long zadd(String key, double score, String member) {
        JedisCommands jc = null;
        try {
            jc = getResource();
            return jc.zadd(key, score, member);
        } finally {
            returnResource(jc);
        }
    }

    @Override
    public long zadd(String key, Map<String, Double> valueScoreMap, int cacheSeconds) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            if (valueScoreMap == null || valueScoreMap.size() == 0 ) {
                throw new RuntimeException("集合不能为空");
            }
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.zadd(key, valueScoreMap);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long zadd(String key, Map<String, Double> valueScoreMap) {
        long result = 0;
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            result = jedis.zadd(key, valueScoreMap);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long zcard(String key) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zcard(key);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public long zcount(String key, double min, double max) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zcount(key, min, max);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public double zincrby(String key, double score, String member) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zincrby(key, score, member);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zrange(key, start, end);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Map<String, Double> zrangeWithScoresToMap(String key, long start, long end) {
        Set<Tuple> tuples = zrangeWithScores(key, start, end);
        Map<String, Double> result = new HashMap<>();
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                result.put(tuple.getElement(), tuple.getScore());
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            Set<Tuple> tuples = jedis.zrangeWithScores(key, start, end);
            return tuples;
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zrangeByScore(key, min, max, offset, count);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zrangeByScoreWithScores(key, min, max);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Map<String, Double> zrangeByScoreWithScoresToMap(String key, double min, double max) {
        Set<Tuple> tuples = zrangeByScoreWithScores(key, min, max);
        Map<String, Double> result = new HashMap<>();
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                result.put(tuple.getElement(), tuple.getScore());
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        JedisCommands jedis = null;
        try {
            jedis = getResource();
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Map<String, Double> zrangeByScoreWithScoresToMap(String key, double min, double max, int offset, int count) {
        Set<Tuple> tuples = zrangeByScoreWithScores(key, min, max, offset, count);
        Map<String, Double> result = new HashMap<>();
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                result.put(tuple.getElement(), tuple.getScore());
            }
        }
        return result;
    }

    /*==============================sort set end  =====================================================*/

}
