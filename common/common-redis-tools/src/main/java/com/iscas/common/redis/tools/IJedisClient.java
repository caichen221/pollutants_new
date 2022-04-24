package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.interfaces.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Jedis操作接口<br/>
 * <p>
 * modify 2020-11-15<br/>
 * 当前此接口仅支持存储对象(当然也支持字符串)，调用了序列化和反序列化<br/>
 * 字符串操作拆分为新接口类{@link IJedisStrClient}
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2018/11/5 14:44
 * @since jdk1.8
 */


public interface IJedisClient extends IJedisCommonClient, IJedisListClient, IJedisSetClient, IJedisSortSetClient, IJedisHashClient,
        IJedisStringClient {

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
     * 按照key表达式规则删除
     */
    void deleteByPattern(String pattern) throws UnsupportedEncodingException;

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




    //https://blog.csdn.net/lbl123xx/article/details/89213943

}
