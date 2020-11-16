package com.iscas.common.redis.tools.interfaces;

import redis.clients.jedis.Tuple;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * set 相关操作接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/16 15:14
 * @since jdk1.8
 */
public interface IJedisSortSetClient {
    /**
     * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/16
     * @param key 缓存的key
     * @param score 权重
     * @param member 缓存的成员
     * @throws
     * @return
     */
    long zadd(String key, double score, Object member) throws IOException;

    /**
     * 设置zSet缓存
     *
     * @param key           键
     * @param valueScoreMap (value和score)
     * @param cacheSeconds  超时时间，0为不超时
     * @return
     */
    long zadd(String key, Map<? extends Object, Double> valueScoreMap, int cacheSeconds) throws IOException;

    /**
     * 向ZSet缓存中添加值
     *
     * @param key           键
     * @param valueScoreMap (value和score)
     * @return
     */
    long zadd(String key, Map<? extends Object, Double> valueScoreMap) throws IOException;

    /**
     * 获取zset中元素的个数
     * @param key 键
     * @return
     */
    long zcard(String key) throws IOException;

    /**
     * 获取zset中指定权重区间内元素的数量
     * @param key 键
     * @return
     */
    long zcount(String key, double min, double max) throws IOException;

    /**
     * 如果给定的member已存在， zset中某个元素的权重增加给定值，如果不存在插入这个元素和对应的权重
     *
     * @param key 键
     * @param score  要增的权重
     * @param member 元素
     * @return double 增后的权重
     */
    double zincrby(String key, double score, Object member) throws IOException;

    /**
     * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
     *
     * @param tClass  返回对象的泛型
     * @param key    键
     * @param start  开始位置(包含)
     * @param end 结束位置(包含)
     * @return
     **/
    <T> Set<T> zrange(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException;

    /**
     * 读取制定范围的zset中的元素并返回Map
     *
     * @param tClass 返回对象的泛型
     * @param key 键
     * @param start  开始位置(包含)
     * @param end 结束位置(包含)
     * @return 值
     */
    <T> Map<T, Double> zrangeWithScoresToMap(Class<T> tClass, String key, long start, long end) throws IOException, ClassNotFoundException;

    /**
     * 读取制定范围的zset中的元素并返回Map,
     * @param key 键
     * @param start  开始位置(包含)
     * @param end 结束位置(包含)
     * @return 值
     */
    Set<Tuple> zrangeWithScores(String key, long start, long end) throws IOException;

    /**
     * 按照权重值查找zset中的元素
     *
     * @param tClass 返回对象的泛型
     * @param key 键
     * @param min  权重最小值
     * @param max  权重最大值
     * @return 值
     */
    <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max) throws IOException, ClassNotFoundException;

    /**
     * 按照权重值查找zset中的元素, 带偏移量
     *
     * @param tClass 返回对象的泛型
     * @param key 键
     * @param min  权重最小值
     * @param max  权重最大值
     * @return 值
     */
    <T> Set<T> zrangeByScore(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException;

    /**
     * 读取指定得分范围的zset中的元素
     *
     * @param key 键
     * @param min  开始位置(包含)
     * @param max 结束位置(包含)
     * @return 值
     */
    Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws IOException, ClassNotFoundException;

    /**
     * 读取指定得分范围的zset中的元素, 返回Map
     * @param tClass 返回对象的泛型
     * @param key 键
     * @param min  开始位置(包含)
     * @param max 结束位置(包含)
     * @return 值
     */
    <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max) throws IOException, ClassNotFoundException;

    /**
     * 读取指定得分范围的zset中的元素, 带偏移量
     *
     * @param key 键
     * @param min  开始位置(包含)
     * @param max 结束位置(包含)
     * @return 值
     */
    Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException;

    /**
     * 读取指定得分范围的zset中的元素, 返回Map, 带偏移量
     * @param tClass 返回对象的泛型
     * @param key 键
     * @param min  开始位置(包含)
     * @param max 结束位置(包含)
     * @return 值
     */
    <T> Map<T, Double> zrangeByScoreWithScoresToMap(Class<T> tClass, String key, double min, double max, int offset, int count) throws IOException, ClassNotFoundException;

}
