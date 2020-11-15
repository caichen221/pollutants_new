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
 * Jedis操作接口,存储字符串，将原来的{@link IJedisStrClient} 中<br/>
 * 存储字符串的方式单独拆分出来，以免造成对象存储与字符串存储调用时混淆
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/15 14:44
 * @since jdk1.8
 */


public interface IJedisStrClient {

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */

    String get(String key);

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean set(String key, String value, int cacheSeconds);

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    List<String> getList(String key);

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setList(String key, List<String> value, int cacheSeconds);

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long listAdd(String key, String... value);

    /**
     * 模拟出队列
     * @param key 键
     * @return
     * */
    String lpopList(String key);

    /**
     * 模拟出栈，存储为字符串
     * @param key 键
     * @return
     * */
    String rpopList(String key);


    /**
     * 为了使函数名与命令更接近，弃用，见新函数{@link #smembers(String)}
     * 获取集合缓存
     * @param key 键
     * @return 值
     */
    Set<String> getSet(String key);


    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setSet(String key, Set<String> value, int cacheSeconds);

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    long setSetAdd(String key, String... value);

    /**
     * 获取zset缓存
     * @param key 键
     * @return 值
     */
    Map<String, Double> getZSet(String key);

    Set<Tuple> getZSetToTuple(String key);

    /**
     * 设置ZSet缓存
     * @param key 键
     * @param valueScoreMap (value和score)
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setZSet(String key, Map<String, Double> valueScoreMap, int cacheSeconds);


    /**
     * 向ZSet缓存中添加值
     * @param key 键
     * @param valueScoreMap (value和score)
     * @return
     */
    long setZSetAdd(String key, Map<String, Double> valueScoreMap);

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
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    boolean mapPut(String key, Map<String, String> value);


    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    long mapRemove(String key, String mapKey);

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    boolean mapExists(String key, String mapKey);

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    long del(String key);


    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    boolean exists(String key);


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
    void expire(String key, long millisecond);

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
     * 将成员从源集合移出放入目标集合 </br>
     * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0 </br>
     * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/11
     * @param srckey 源集合的key
     * @param dstkey 目标集合的key
     * @param member 源集合中的元素
     * @throws
     * @return 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0
     */
    long smove(String srckey, String dstkey, String member);


    /**
     * 从集合中移除成员并返回
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/11
     * @param key 集合的key
     * @throws
     * @return
     */
    String spop(String key);

    /**
     * 从集合中移除成员并返回
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/11
     * @param key 集合的key
     * @param count 移除集合的数目
     * @throws
     * @return
     */
    Set<String> spop(String key, long count);

    /**
     * 从集合中移除某个成员，成功返回1，成员不存在返回0,移除多个元素时，如果移除了其中一个就返回1
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/15
     * @param
     * @throws
     * @return
     */
    long srem(String key, String... member);



    //https://blog.csdn.net/lbl123xx/article/details/89213943

}
