package com.iscas.common.redis.tools;

import redis.clients.jedis.Tuple;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Jedis操作接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/5 14:44
 * @since jdk1.8
 */


public interface IJedisClient {

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */

    String get(String key);

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    Object getObject(String key) throws IOException, ClassNotFoundException;

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean set(String key, String value, int cacheSeconds);

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean setObject(String key, Object value, int cacheSeconds) throws IOException;

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    List<String> getList(String key);

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    List<Object> getObjectList(String key) throws IOException, ClassNotFoundException;

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setList(String key, List<String> value, int cacheSeconds);

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setObjectList(String key, List<Object> value, int cacheSeconds) throws IOException;

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long listAdd(String key, String... value);

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long listObjectAdd(String key, Object... value) throws IOException;

    /**
     * 模拟出队列
     * @param key 键
     * @return
     * */
    String lpopList(String key);

    /**
     * 模拟出队列，存储为对象
     * @param key 键
     * @return
     * */
    Object lpopObjectList(String key) throws IOException, ClassNotFoundException;

    /**
     * 模拟出栈，存储为字符串
     * @param key 键
     * @return
     * */
    String rpopList(String key);

    /**
     * 模拟出栈，存储为对象
     * @param key 键
     * @return
     * */
    Object rpopObjectList(String key) throws IOException, ClassNotFoundException;

    /**
     * 为了使函数名与命令更接近，弃用，见新函数{@link #smembers(String)}
     * 获取集合缓存
     * @param key 键
     * @return 值
     */
    Set<String> getSet(String key);

    /**
     * 为了使函数名与命令更接近，弃用，见新函数{@link #smembers(String)}
     * 获取集合缓存
     * @param key 键
     * @return 值
     */
    Set<Object> getObjectSet(String key) throws IOException, ClassNotFoundException;

    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setSet(String key, Set<String> value, int cacheSeconds);

    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setObjectSet(String key, Set<Object> value, int cacheSeconds) throws IOException;

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long setSetAdd(String key, String... value);

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long setSetObjectAdd(String key, Object... value) throws IOException;


    /**
     * 获取zset缓存
     * @param key 键
     * @return 值
     */
    Map<String, Double> getZSet(String key);

    Set<Tuple> getZSetToTuple(String key);

    /**
     * 获取zSET缓存
     * @param key 键
     * @return 值
     */
    Map<Object, Double> getObjectZSet(String key) throws IOException, ClassNotFoundException;

    /**
     * 设置ZSet缓存
     * @param key 键
     * @param valueScoreMap (value和score)
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setZSet(String key, Map<String, Double> valueScoreMap, int cacheSeconds);

    /**
     * 设置zSet缓存
     * @param key 键
     * @param valueScoreMap (value和score)
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setObjectZSet(String key, Map<Object, Double> valueScoreMap, int cacheSeconds) throws IOException;

    /**
     * 向ZSet缓存中添加值
     * @param key 键
     * @param valueScoreMap (value和score)
     * @return
     */
    long setZSetAdd(String key, Map<String, Double> valueScoreMap);

    /**
     * 向ZSet缓存中添加值
     * @param key 键
     * @param valueScoreMap (value和score)
     * @return
     */
    long setZSetObjectAdd(String key, Map<Object, Double> valueScoreMap) throws IOException;



    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    Map<String, String> getMap(String key);


    String getMapByMapKey(String key, String mapKey);


    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    Map<byte[], byte[]> getBytesMap(byte[] key);

    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    Map<String, Object> getObjectMap(String key) throws IOException, ClassNotFoundException;

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean setMap(String key, Map<String, String> value, int cacheSeconds);

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean setBytesMap(byte[] key, Map<byte[], byte[]> value, int cacheSeconds);

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean setObjectMap(String key, Map<String, Object> value,
                         int cacheSeconds) throws IOException;

    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    boolean mapPut(String key, Map<String, String> value);

    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    boolean mapObjectPut(String key, Map<String, Object> value) throws IOException;

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    long mapRemove(String key, String mapKey);

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    long mapObjectRemove(String key, String mapKey) throws IOException;

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    boolean mapExists(String key, String mapKey);

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    boolean mapObjectExists(String key, String mapKey) throws IOException;

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    long del(String key);

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    long delObject(String key) throws IOException;

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    boolean exists(String key);

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    boolean existsObject(String key) throws IOException;


    /**
     * 获取分布式锁
     * @param lockName 锁key
     * @param lockTimeoutInMS 锁超时时间
     * @return 锁标识
     */
    String acquireLock(String lockName, long lockTimeoutInMS);

    /**
     * 释放分布式锁
     * @param lockName 锁key
     * @param identifier 锁标识
     * @return
     */
    boolean releaseLock(String lockName, String identifier);


    /**
     * 对IP进行限流
     * */
    boolean accessLimit(String ip, int timeout, int limit);

    /**
     * 按照key表达式规则删除
     * */
    void deleteByPattern(String pattern) throws UnsupportedEncodingException;


    /**
     * redis实现延时队列，并放入任务
     * @param task 放入的任务
     * @param timeout 延时时间
     * @param timeUnit 延时时间单位
     * @param consumer 消费者,这里定义延时时间到后的处理, 建议执行处理采用异步的方式
     * */
    void putDelayQueue(String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer);

    /**
     * redis实现延时队列，并放入任务
     * @param key 放入的key
     * @param task 放入的任务
     * @param timeout 延时时间
     * @param timeUnit 延时时间单位
     * @param consumer 消费者,这里定义延时时间到后的处理，建议执行处理采用异步的方式
     * */
    void putDelayQueue(String key, String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer);

    /**
     * 设置过期时间
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/2
     * @param key 缓存的key
     * @param millisecond 过期时间毫秒
     * @throws
     * @return
     */
    void expire(String key, int millisecond);

    /**
     * 获取集合中元素的个数
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/2
     * @param key 集合的key
     * @throws
     * @return
     */
    long scard(String key);

    /**
     * 返回从第一个Key和其他key的集合之间的差异的成员，如果没有差异，返回空集合
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/4
     * @param keys 集合的keys
     * @return Set 差集
     */
    Set<String> sdiff(String... keys);

    /**
     * 返回从第一个Key和其他key的集合之间的差异的成员，返回结果为反序列的对象，放入时也必须是非字符串的对象。如果没有差异，返回空集合
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/4
     * @param keys 集合的keys
     * @return Set 差集
     */
    Set<Object> sdiffObject(String... keys) throws IOException, ClassNotFoundException;

    /**
     * 这个命令等于{@link #sdiff},但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param newkey 存储的心集合
     * @param keys 要取差集的集合的key
     * @throws
     * @return long 新集合中的记录数
     */
    long sdiffStore(String newkey, String... keys);

    /**
     * 这个命令等于{@link #sdiffObject},但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param newkey 存储的心集合
     * @param keys 要取差集的集合的key
     * @throws
     * @return long 新集合中的记录数
     */
    long sdiffObjectStore(String newkey, String... keys) throws IOException, ClassNotFoundException;

    /**
     * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param keys 要取差集的集合的key
     * @throws
     * @return Set<String> 交集的集合
     */
    Set<String> sinter(String... keys);

    /**
     * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set,适用于存放对象的集合
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param keys 要取差集的集合的key
     * @throws
     * @return Set<Object> 交集的集合
     */
    Set<Object> sinterObject(String... keys) throws IOException, ClassNotFoundException;

    /**
     * 个命令等于{@link #sinter},但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param keys 要取差集的集合的key
     * @throws
     * @return long 交集的条目
     */
    long sinterStore(String newKey, String... keys);

    /**
     * 个命令等于{@link #sinterObject},但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param keys 要取差集的集合的key
     * @throws
     * @return long 交集的条目
     */
    long sinterObjectStore(String newKey, String... keys) throws IOException;

    /**
     * 判断一个给定的值在集合中是否存在
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param key 集合的key
     * @param member 成员
     * @throws
     * @return boolean true:存在 false:不存在
     */
    boolean sismember(String key, String member);

    /**
     * 判断一个给定的值在集合中是否存在,集合中存储的为对象
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param key 集合的key
     * @param member 成员
     * @throws
     * @return boolean true:存在 false:不存在
     */
    boolean sismemberObject(String key, Object member) throws IOException;

    /**
     * 返回集合中的所有成员，成员为字符串
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param key 集合的key
     * @throws
     * @return Set<String> 集合中的所有值
     */
    Set<String> smembers(String key);

    /**
     * 返回集合中的所有成员, 成员为对象
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/6
     * @param key 集合的key
     * @throws
     * @return Set<String> 集合中的所有值
     */
    Set<Object> smembersObject(String key) throws IOException, ClassNotFoundException;

    //https://blog.csdn.net/lbl123xx/article/details/89213943

}
