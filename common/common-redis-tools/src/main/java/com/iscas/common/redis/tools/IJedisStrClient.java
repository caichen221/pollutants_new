package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.interfaces.IJedisHashStrClient;
import com.iscas.common.redis.tools.interfaces.IJedisListStrClient;
import com.iscas.common.redis.tools.interfaces.IJedisSetStrClient;
import com.iscas.common.redis.tools.interfaces.IJedisSortSetStrClient;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
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


public interface IJedisStrClient extends IJedisSetStrClient, IJedisListStrClient, IJedisSortSetStrClient, IJedisHashStrClient {

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



    String getMapByMapKey(String key, String mapKey);


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



    //https://blog.csdn.net/lbl123xx/article/details/89213943

}
