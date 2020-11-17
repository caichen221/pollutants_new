package com.iscas.common.redis.tools.interfaces;

import java.util.Map;

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

}
