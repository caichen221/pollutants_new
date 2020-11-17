package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.interfaces.IJedisHashClient;
import com.iscas.common.redis.tools.interfaces.IJedisListClient;
import com.iscas.common.redis.tools.interfaces.IJedisSetClient;
import com.iscas.common.redis.tools.interfaces.IJedisSortSetClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Jedis操作接口<br/>
 * <p>
 * modify 2020-11-15<br/>
 * 当前此接口仅支持存储对象(当然也支持字符串)，调用了序列化和反序列化<br/>
 * 字符串操作拆分为新接口类{@link IJedisStrClient}
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/5 14:44
 * @since jdk1.8
 */


public interface IJedisClient extends IJedisListClient, IJedisSetClient, IJedisSortSetClient, IJedisHashClient {

    /**
     * 获取缓存
     *
     * @param tClass 对象泛型
     * @param key    键
     * @return 值
     */
    <T> T get(Class<T> tClass, String key) throws IOException, ClassNotFoundException;

    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    boolean set(String key, Object value, int cacheSeconds) throws IOException;

    /**
     * 获取List缓存
     *
     * @param tClass 对象泛型
     * @param key    键
     * @return 值
     */
    <T> List<T> getList(Class<T> tClass, String key) throws IOException, ClassNotFoundException;

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    long setList(String key, List value, int cacheSeconds) throws IOException;

    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    long listAdd(String key, Object... value) throws IOException;

    /**
     * 模拟出队列，存储为对象
     *
     * @param tClass 对象泛型
     * @param key    键
     * @return
     */
    <T> T lpopList(Class<T> tClass, String key) throws IOException, ClassNotFoundException;

    /**
     * 模拟出栈，存储为对象
     *
     * @param tClass 对象泛型
     * @param key    键
     * @return
     */
    <T> T rpopList(Class<T> tClass, String key) throws IOException, ClassNotFoundException;

    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    Map<byte[], byte[]> getBytesMap(byte[] key);

    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    Map<String, Object> getMap(String key) throws IOException, ClassNotFoundException;

    /**
     * 移除Map缓存中的值
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    long mapRemove(String key, String mapKey) throws IOException;

    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    boolean mapExists(String key, String mapKey) throws IOException;

    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    long del(String key) throws IOException;

    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    boolean exists(String key) throws IOException;


    /**
     * 获取分布式锁
     *
     * @param lockName        锁key
     * @param lockTimeoutInMS 锁超时时间
     * @return 锁标识
     */
    String acquireLock(String lockName, long lockTimeoutInMS);

    /**
     * 释放分布式锁
     *
     * @param lockName   锁key
     * @param identifier 锁标识
     * @return
     */
    boolean releaseLock(String lockName, String identifier);


    /**
     * 对IP进行限流
     */
    boolean accessLimit(String ip, int timeout, int limit);

    /**
     * 按照key表达式规则删除
     */
    void deleteByPattern(String pattern) throws UnsupportedEncodingException;


//    /**
//     * redis实现延时队列，并放入任务
//     * @param task 放入的任务
//     * @param timeout 延时时间
//     * @param timeUnit 延时时间单位
//     * @param consumer 消费者,这里定义延时时间到后的处理, 建议执行处理采用异步的方式
//     * */
//    void putDelayQueue(String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer);
//
//    /**
//     * redis实现延时队列，并放入任务
//     * @param key 放入的key
//     * @param task 放入的任务
//     * @param timeout 延时时间
//     * @param timeUnit 延时时间单位
//     * @param consumer 消费者,这里定义延时时间到后的处理，建议执行处理采用异步的方式
//     * */
//    void putDelayQueue(String key, String task, long timeout, TimeUnit timeUnit, Consumer<String> consumer);

    /**
     * 设置对象存储的过期时间
     *
     * @param key 缓存的key
     * @param millisecond 过期时间毫秒
     * @return
     * @throws
     * @version 1.0
     * @date 2020/11/2
     * @since jdk1.8
     */
    void expire(String key, long millisecond) throws IOException;


    //https://blog.csdn.net/lbl123xx/article/details/89213943

}
