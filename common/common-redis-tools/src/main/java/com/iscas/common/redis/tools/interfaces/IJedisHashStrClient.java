package com.iscas.common.redis.tools.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jedis-hash操作接口，适用于字符串，自动序列化与反序列化
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/17 15:50
 * @since jdk1.8
 */
public interface IJedisHashStrClient {

    /**
     * 存入hash
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param map 存储的键值对
     * @param cacheSenconds 缓存的时间，0代表永久
     * @throws
     * @return 成功返回true
     */
    boolean hmset(String key, Map<String, String> map, int cacheSenconds);

    /**
     * 从hash中获取所有元素存入map
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @throws
     * @return
     */
    Map<String, String> hgetAll(String key);

    /**
     * 从hash中删除元素
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param fields hash的key
     * @throws
     * @return 删除的元素数量
     */
    long hdel(String key, String... fields);

    /**
     * 从hash中删除元素
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param field hash的key
     * @throws
     * @return
     */
    boolean hexists(String key, String field);

    /**
     * 从hash中获取指定key的值
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param field hash的key
     * @throws
     * @return
     */
    String hget(String key, String field);

    /**
     * 向hash中添加一个键值对
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param field hash的key
     * @param value hash的value
     * @throws
     * @return
     */
    long hset(String key, String field, String value);

    /**
     * 增加hash一个条目，只有在field不存在时才执行
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @param field hash的key
     * @param value hash的value
     * @throws
     * @return
     */
    long hsetnx(String key, String field, String value);

    /**
     * 获取hash中value的集合
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key key
     * @throws
     * @return
     */
    List<String> hvals(String key);

    /**
     * hash中的字段值加上指定增量值,存入的值要能转为整数
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key   哈希表的键
     * @param field
     * @param value 要增加的值,可以是负数
     * @throws
     * @return
     */
    long hincrby(String key, String field, long value);

    /**
     * hash中的字段值加上指定浮点增量值,存入的值要能转为整数
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key   哈希表的键
     * @param field
     * @param value 要增加的值,可以是负数
     * @throws
     * @return
     */
    Double hincrby(String key, String field, double value);

    /**
     * 返回指定hash中的所有存储的field,类似Map中的keySet方法
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key   哈希表的键
     * @throws
     * @return
     */
    Set<String> hkeys(String key);

    /**
     * 获取hash中存储的个数，类似Map中size方法
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key   哈希表的键
     * @throws
     * @return
     */
    long hlen(String key);

    /**
     * 从hash中，根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/17
     * @param key   哈希表的键
     * @param fields   哈希表的key
     * @throws
     * @return
     */
    List<String> hmget(String key, String... fields);

}
