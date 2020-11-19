package com.iscas.common.redis.tools.interfaces;

import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * List(链表) 相关操作接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/16 11:13
 * @since jdk1.8
 */
public interface IJedisListStrClient {

    /**
     * 向list中尾部追加元素
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key key
     * @param value 元素
     * @throws
     * @return 追加元素后链表的长度
     */
    long rpush(String key, String... value);

    /**
     * 向list中头部追加元素
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key key
     * @param value 元素
     * @throws
     * @return 追加元素后链表的长度
     */
    long lpush(String key, String... value);

    /**
     * 获取list的长度
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key key
     * @throws
     * @return 追加元素后链表的长度
     */
    long llen(String key);

    /**
     * 覆盖List中指定位置的值
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key key
     * @param index 下标
     * @param value 值
     * @throws
     * @return 追加元素后链表的长度
     */
    boolean lset(String key, int index, String value);

    /**
     * 在value的相对位置插入记录
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @param where 前面插入或后面插入
     * @param pivot 相对位置的内容
     * @param value 插入的内容
     * @throws
     * @return 追加元素后链表的长度
     */
    long linsert(String key, ListPosition where, String pivot, String value);

    /**
     * 获取List中指定位置的值
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @param index 位置下标
     * @throws
     * @return
     */
    String lindex(String key, long index);

    /**
     * 将List中的第一条记录移出List
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @throws
     * @return
     */
    String lpop(String key);

    /**
     * 将List中的最后一条记录移出List
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @throws
     * @return
     */
    String rpop(String key);

    /**
     * 获取指定范围的记录，可以做为分页使用
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @param start 开始位置
     * @param end 结束位置
     * @throws
     * @return
     */
    List<String> lrange(String key, long start, long end);

    /**
     * 删除List中count条记录，被删除的记录值为value
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @param count 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param value 要匹配的值
     * @throws
     * @return 删除的记录数
     */
    long lrem(String key, int count, String value);

    /**
     * 删除记录，只保留start与end之间的记录
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/19
     * @param key
     * @param start 开始位置
     * @param end 结束位置
     * @throws
     * @return
     */
    boolean ltrim(String key, long start, long end);



}
