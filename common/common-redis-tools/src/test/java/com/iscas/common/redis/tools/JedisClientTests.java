package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.impl.JedisClient;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.Tuple;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单机redis测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/5 15:36
 * @since jdk1.8
 */
public class JedisClientTests {
    private JedisClient jedisClient;
    private int goodsCount = 50;

    @Before
    @Ignore
    public void before() {
        JedisConnection jedisConnection = new JedisStandAloneConnection();
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setMaxIdle(10);
        configInfo.setMaxTotal(50);
        configInfo.setMaxWait(20000);
        RedisInfo redisInfo = new RedisInfo();
        redisInfo.setHost("localhost");
        redisInfo.setPort(6379);
//        redisInfo.setPwd("iscas");
        redisInfo.setTimeout(10000);
        configInfo.setRedisInfos(Arrays.asList(redisInfo));
        jedisClient = new JedisClient(jedisConnection, configInfo);

    }

    //集群
//    @Before
//    @Ignore
//    public void before() {
//        JedisClusterConnection jedisConnection = new JedisClusterConnection();
//        ConfigInfo configInfo = new ConfigInfo();
//
//        configInfo.setClusterMaxAttempts(10);
//        configInfo.setClusterPassword("iscas");
//        configInfo.setClusterTimeout(2000);
//        configInfo.setClusterSoTimeout(2000);
//        RedisInfo redisInfo = new RedisInfo("localhost", 6379, 10000, "iscas");
//        configInfo.setRedisInfos(Arrays.asList(redisInfo));
//        jedisClient = new JedisStrClient(jedisConnection, configInfo);
//    }

    //shared
//    @Before
//    @Ignore
//    public void before() {
//        JedisConnection jedisConnection = new JedisShardConnection();
//        ConfigInfo configInfo = new ConfigInfo();
//        configInfo.setMaxIdle(10);
//        configInfo.setMaxTotal(50);
//        configInfo.setMaxWait(20000);
//        RedisInfo redisInfo = new RedisInfo("localhost", 6379, 10000, "iscas");
//        configInfo.setRedisInfos(Arrays.asList(redisInfo));
//        jedisClient = new JedisStrClient(jedisConnection, configInfo);
//    }

    /*=====================================测试SET BEGIN=================================================*/

    /**
     * 测试设置集合，并测试获取集合的值
     */
    @Test
    @Ignore
    public void testSadd() throws IOException, ClassNotFoundException {
        try {
            Set<Object> set = new HashSet<>();
            set.add("x");
            set.add("y");
            set.add("z");
            set.add("m");
            long result = jedisClient.sadd("testKey", set, 0);
            Set resultSet = jedisClient.smembers(Object.class, "testKey");
            Assert.assertEquals(4, result);
            resultSet.forEach(System.out::println);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试集合追加值，数据为对象
     */
    @Test
    @Ignore
    public void testSdd2() throws IOException, ClassNotFoundException {
        try {
            long result = jedisClient.sadd("testKey", 1, 2, 4, "wegw");
            Set resultSet = jedisClient.smembers(Object.class, "testKey");
            Assert.assertEquals(4, result);
            resultSet.forEach(System.out::println);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试交集
     */
    @Test
    public void testSinter() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "22222", "33333", 45);
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "22222", "44444", 45);
            Set<Object> result = jedisClient.sinter(Object.class, "testKey", "testKey2");
            result.stream().forEach(System.out::println);
            Assert.assertEquals(3, result.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试交集,存入新集合
     */
    @Test
    public void testSinterStore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333", 67);
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "44444", 67);
            long result = jedisClient.sinterStore("newKey", "testKey", "testKey2");
            Set<Object> newSet = jedisClient.smembers(Object.class, "newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(2, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("newKey");
        }
    }

    /**
     * 测试集合中某个元素是否存在
     */
    @Test
    public void testSismember() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333", 1111);
            boolean exists = jedisClient.sismember("testKey", 1111);
            Assert.assertEquals(true, exists);
        } finally {
            jedisClient.del("testKey");
        }
    }


    /**
     * 测试获取集合中所有的元素,集合中存储为对象
     */
    @Test
    public void testSmembers() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333", 23455);
            Set<Object> members = jedisClient.smembers(Object.class, "testKey");
            members.stream().forEach(System.out::println);
            Assert.assertEquals(3, members.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中移除对象成员,放入目标集合
     */
    @Test
    public void testSmove() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
            jedisClient.sadd("srcKey", "11111", "33333");
            jedisClient.sadd("dstKey", "2222", "5555");
            jedisClient.smove("srcKey", "dstKey", "11111");
            Set<Object> srcSet = jedisClient.smembers(Object.class, "srcKey");
            Set<Object> dstSet = jedisClient.smembers(Object.class, "dstKey");
            srcSet.forEach(System.out::println);
            dstSet.forEach(System.out::println);
            Assert.assertEquals(1, srcSet.size());
            Assert.assertEquals(3, dstSet.size());
        } finally {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
        }
    }


    /**
     * 测试从集合中随机移除对象成员并返回
     */
    @Test
    public void testSpop() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            Object data = jedisClient.spop(Object.class, "testKey");
            System.out.println(data);
            Assert.assertEquals(1, jedisClient.scard("testKey"));
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取集合中对象元素的个数
     */
    @Test
    public void testScard() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", 2, 3, 4);
            long sum = jedisClient.scard("testKey");
            Assert.assertEquals(3, sum);
        } finally {
            jedisClient.del("testKey");
        }
    }


    /**
     * 测试从集合中随机移除对象成员并返回
     */
    @Test
    public void testSpopCount() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333", 3);
            Set<Object> datas = jedisClient.spop(Object.class, "testKey", 3);
            datas.forEach(System.out::println);
            Assert.assertEquals(3, datas.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中删除成员
     */
    @Test
    public void testSrem() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", 33333);
            long result = jedisClient.srem("testKey", "11111", "44444");
            Set<Object> set = jedisClient.smembers(Object.class, "testKey");
            set.forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试去并集
     */
    @Test
    public void testSunion() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey", "1", "2", "3", 2222);
            jedisClient.sadd("testKey2", "1", "2", "3", "7", 345);
            Set sunionSet = jedisClient.sunion(Object.class, "testKey", "testKey2");
            sunionSet.forEach(System.out::println);
            Assert.assertEquals(6, sunionSet.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试sdiff 差集
     */
    @Test
    @Ignore
    public void testSdiff() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            long result = jedisClient.sadd("testKey", 1, 2, 4, "wegw");
            long result2 = jedisClient.sadd("testKey2", 1, 2, 6, 7);
            Set resultSet = jedisClient.sdiff(Object.class, "testKey", "testKey2");
            Assert.assertEquals(2, resultSet.size());
            resultSet.forEach(System.out::println);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试sdiff 差集
     */
    @Test
    @Ignore
    public void testSdiffStore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
            long result = jedisClient.sadd("testKey", 1, 2, 4, "wegw");
            long result2 = jedisClient.sadd("testKey2", 1, 2, 6, 7);
            jedisClient.sdiffStore("testKey3", "testKey", "testKey2");
            Set<Object> key3Result = jedisClient.smembers(Object.class, "testKey3");
            Assert.assertEquals(2, key3Result.size());
            key3Result.forEach(System.out::println);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
        }
    }

    /*=====================================测试SET END===================================================*/

    /**
     * 测试插入一个对象，有超时时间，并测试获取这个对象
     */
    @Test
    @Ignore
    public void testSetAndGet() throws IOException, ClassNotFoundException {
        try {
            boolean result = jedisClient.set("testKey", "value", 20);
            String testValue = jedisClient.get(String.class, "testKey");
            Assert.assertTrue(result);
            Assert.assertEquals(testValue, "value");
        } finally {
            jedisClient.del("testKey");
        }
    }


    /**
     * 测试设置List数据,数据为对象, 并测试获取List数据
     */
    @Test
    @Ignore
    public void testSetAndGetList() throws IOException, ClassNotFoundException {
        try {
            long result = jedisClient.setList("testKey", Arrays.asList("4", "5", "6"), 20);
            List<String> list = jedisClient.getList(String.class, "testKey");
            Assert.assertEquals(3, result);
            list.forEach(System.out::println);
            Assert.assertEquals(list.size(), 3);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试向List添加数据,数据为对象
     */
    @Test
    @Ignore
    public void testListAdd() throws IOException, ClassNotFoundException {
        try {
            long result = jedisClient.listAdd("testKey", "x", "y", "z");
            Assert.assertEquals(3, result);
            result = jedisClient.listAdd("testKey", 11, 333);
            Assert.assertEquals(5, result);
            List<Object> list = jedisClient.getList(Object.class, "testKey");
            list.forEach(System.out::println);
        } finally {
            jedisClient.del("testKey");
        }

    }

    /**
     * 测试从List pop数据,数据为对象，适用于队列模式
     */
    @Test
    @Ignore
    public void testLpopList() throws IOException, ClassNotFoundException {
        try {
            jedisClient.listAdd("testKey", "x", "y", "z");
            String result = jedisClient.lpopList(String.class, "testKey");
            Assert.assertEquals("x", result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从List pop数据,数据为对象，适用于队列模式
     */
    @Test
    @Ignore
    public void testRpopList() throws IOException, ClassNotFoundException {
        try {
            jedisClient.listAdd("testKey", "x", "y", "z");
            String result = jedisClient.rpopList(String.class, "testKey");
            Assert.assertEquals("z", result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 移除某个Map值,类型为对象
     */
    @Test
    @Ignore
    public void test29() throws IOException {
        long l = jedisClient.mapRemove("map2", "g");
        System.out.println(l);
    }

    /**
     * 判断Map中某个值是否存在
     */
    @Test
    @Ignore
    public void test31() throws IOException {
        boolean b = jedisClient.mapExists("map2", "a");
        System.out.println(b);
    }


    /**
     * 测试分布式锁
     */
    @Test
    @Ignore
    public void test32() throws InterruptedException {
        //JVM 锁
        final Object lock = new Object();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = () -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 5; i++) {
                //不使用任何锁
//                goodsCount--;

                //使用JVM锁
//                synchronized (lock) {
//                    goodsCount--;
//                }

                //使用redis作分布式锁
                String lockFlag = null;
                while (true) {
                    lockFlag = jedisClient.acquireLock("goodscount", 100000);
                    if (lockFlag != null) {
                        goodsCount--;
                        break;
                    }
                }
                jedisClient.releaseLock("goodscount", lockFlag);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(runnable);
            countDownLatch.countDown();
        }
//        // 假设一个足够长的时间去验证最后的goodCount
        for (int i = 0; i < 10; i++) {
            System.out.println(goodsCount);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * 测试分布式限流
     */
    @Test
    @Ignore
    public void test33() {
        for (int i = 0; i < 150; i++) {
            boolean flag = jedisClient.accessLimit("localhost", 10, 100);
            System.out.println(flag);
        }

    }


    /**
     * 测试设置存储对象的key的过期时间
     */
    @Test
    public void test54() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.set("testKey", "11111", 0);
            jedisClient.expire("testKey", 5000);
            Assert.assertEquals("11111", jedisClient.get(Object.class, "testKey"));
            TimeUnit.SECONDS.sleep(6);
            Assert.assertNull(jedisClient.get(Object.class, "testKey"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            jedisClient.del("testKey");
        }
    }

    /*=============================sort set begin======================================*/
    /**
     * 测试将一个元素插入zset
     * */
    @Test
    public void testZadd() throws IOException {
        try {
            jedisClient.del("testKey");
            long result = jedisClient.zadd("testKey", 1, 123);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试将多个元素插入zset
     * */
    @Test
    public void testZadd2() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<Integer, Double> map = new HashMap<>();
            map.put(1, 6.7);
            map.put(2, 12.9);
            long result = jedisClient.zadd("testKey", map);
            Assert.assertEquals(2, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试将多个元素插入zset, 带超时时间
     * */
    @Test
    public void testZadd3() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<Integer, Double> map = new HashMap<>();
            map.put(1, 6.7);
            map.put(2, 12.9);
            long result = jedisClient.zadd("testKey", map,  2);
            Assert.assertEquals(2, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试zset中元素的个数
     * */
    @Test
    public void testZcard() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<Integer, Double> map = new HashMap<>();
            map.put(1, 6.7);
            map.put(2, 12.9);
            jedisClient.zadd("testKey", map,  20);
            long result = jedisClient.zcard("testKey");
            Assert.assertEquals(2, result);
        } finally {
            jedisClient.del("testKey");
        }
    }
    /**
     * 测试获取zset中制定权重区间内的元素的个数
     * */
    @Test
    public void testZcount() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zcount("testKey", 1.0, 1.6);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试为zset中某个元素增加权重
     * */
    @Test
    public void testZincrby() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            double result = jedisClient.zincrby("testKey", 3, "2");
            Assert.assertEquals(5.0, result, 1);
            //如果元素不存在，增加这个元素
            double result2 = jedisClient.zincrby("testKey", 3, "4");
            System.out.println(result2);
            System.out.println(jedisClient.zcard("testKey"));
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取zset制定范围的元素
     * */
    @Test
    public void testZrange() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            Set<String> set1 = jedisClient.zrange(String.class, "testKey", 0, -1);
            set1.forEach(System.out::println);
            //如果元素不存在，增加这个元素
            Set<String> set2 = jedisClient.zrange(String.class, "testKey", 0, 0);
            Assert.assertEquals("1", set2.iterator().next());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取zset制定范围的元素,并附带权重
     * */
    @Test
    public void testZrangeWithScores() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            Set<Tuple> tuples = jedisClient.zrangeWithScores("testKey", 0, -1);
            tuples.forEach(tuple -> {
                try {
                    System.out.println(MyObjectHelper.unserialize(tuple.getBinaryElement()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(tuple.getScore());
            });
            Assert.assertEquals(2, tuples.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取zset制定范围的元素,并附带权重,返回Map
     * */
    @Test
    public void testZrangeWithScoresToMap() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            Map<String, Double> map = jedisClient.zrangeWithScoresToMap(String.class, "testKey", 0, -1);
            map.entrySet().forEach(entry -> {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            });
            Assert.assertEquals(2, map.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素
     * */
    @Test
    public void testZrangeByScore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            jedisClient.zadd("testKey", memebers);
            Set<String> result = jedisClient.zrangeByScore(String.class, "testKey", 1.0, 1.7);
            result.forEach(System.out::println);
            Assert.assertEquals(1, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素, 带偏移量
     * */
    @SneakyThrows
    @Test
    public void testZrangeByScore2() {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Set<String> result = jedisClient.zrangeByScore(String.class, "testKey", 1.0, 4, 1, 2);
            result.forEach(System.out::println);
            Assert.assertEquals(2, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素,返回值附带权重
     * */
    @Test
    public void testZrangeByScoreWithScores() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Set<Tuple> result = jedisClient.zrangeByScoreWithScores("testKey", 1.0, 2.0);
            result.forEach(tuple -> {
                try {
                    System.out.println(MyObjectHelper.unserialize(tuple.getBinaryElement()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(tuple.getScore());
            });
            Assert.assertEquals(2, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素,返回值附带权重,返回Map
     * */
    @Test
    public void testZrangeByScoreWithScoresToMap() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Map<String, Double> result = jedisClient.zrangeByScoreWithScoresToMap(String.class, "testKey", 1.0, 2.0);
            result.entrySet().forEach(System.out::println);
            Assert.assertEquals(2, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素,返回值附带权重, 带偏移量
     * */
    @Test
    public void testZrangeByScoreWithScores2() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Set<Tuple> result = jedisClient.zrangeByScoreWithScores("testKey", 1.0, 2.0, 0, 1);
            result.forEach(tuple -> {
                try {
                    System.out.println(MyObjectHelper.unserialize(tuple.getBinaryElement()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(tuple.getScore());
            });
            Assert.assertEquals(1, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素,返回值附带权重,返回Map, 带偏移量
     * */
    @Test
    public void testZrangeByScoreWithScoresToMap2() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Map<String, Double> result = jedisClient.zrangeByScoreWithScoresToMap(String.class, "testKey", 1.0, 2.0, 0, 1);
            result.entrySet().forEach(System.out::println);
            Assert.assertEquals(1, result.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试按照权重查找zset中元素,返回值附带权重, 返回Map，带偏移量
     * */
    @Test
    public void testZrank() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zrank("testKey", "2");
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从高到低权重排列，某个元素的位置
     * */
    @Test
    public void testZrevrank() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zrevrank("testKey", "2");
            Assert.assertEquals(3, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从zset中删除元素
     * */
    @Test
    public void testZrem() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zrem("testKey", "2", "5");
            Assert.assertEquals(2, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从zset中删除指定位置的元素
     * */
    @Test
    public void testZremrangeByRank() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zremrangeByRank("testKey", 2, 4);
            Assert.assertEquals(3, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从zset中删除指定群众的元素
     * */
    @Test
    public void testZremrangeByScore() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            long result = jedisClient.zremrangeByScore("testKey", 1.0, 3.7);
            Assert.assertEquals(3, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试指定元素在zset中的权重
     * */
    @Test
    public void testZscore() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);
            Double result1 = jedisClient.zscore("testKey", "1");
            Assert.assertEquals(1.0, result1, 1);
            Double result2 = jedisClient.zscore("testKey", "88");
            Assert.assertNull(result2);
        } finally {
            jedisClient.del("testKey");
        }
    }

//    /**
//     * 测试通过字典区间返回有序集合的成员
//     * */
//    @Test
//    public void testZrangeByLex() throws IOException, ClassNotFoundException {
//        try {
//            jedisClient.del("testKey");
//            Map<String, Double> memebers = new HashMap<>();
//            memebers.put("1", 1.0);
//            memebers.put("2", 2.0);
//            memebers.put("3", 3.0);
//            memebers.put("4", 4.0);
//            memebers.put("5", 5.0);
//            jedisClient.zadd("testKey", memebers);
////            Set<String> result1 = jedisClient.zrangeByLex(String.class, "testKey", "-", "+");
////            result1.forEach(System.out::println);
////            Assert.assertEquals(5, result1.size());
//
//            Set<String> result2 = jedisClient.zrangeByLex(String.class, "testKey", "[2", "(4");
//            result2.forEach(System.out::println);
//            Assert.assertEquals(2, result2.size());
//
//        } finally {
//            jedisClient.del("testKey");
//        }
//    }

    /**
     * 测试zset取交集并存入新集合
     * */
    @Test
    public void testZinterstore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);

            Map<String, Double> memebers2 = new HashMap<>();
            memebers2.put("1", 1.0);
            memebers2.put("2", 2.0);
            memebers2.put("3", 3.0);
            memebers2.put("4", 4.0);
            memebers2.put("6", 6.0);
            jedisClient.zadd("testKey2", memebers2);
            long result = jedisClient.zinterstore("testKey3", "testKey", "testKey2");
            Set<String> set = jedisClient.zrange(String.class, "testKey3", 0, -1);
            set.forEach(System.out::println);
            Assert.assertEquals(result, 4);

        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
        }
    }

    /**
     * 测试zset取交集并存入新集合
     * */
    @Test
    public void testZunionstore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
            Map<String, Double> memebers = new HashMap<>();
            memebers.put("1", 1.0);
            memebers.put("2", 2.0);
            memebers.put("3", 3.0);
            memebers.put("4", 4.0);
            memebers.put("5", 5.0);
            jedisClient.zadd("testKey", memebers);

            Map<String, Double> memebers2 = new HashMap<>();
            memebers2.put("1", 1.0);
            memebers2.put("2", 2.0);
            memebers2.put("3", 3.0);
            memebers2.put("4", 4.0);
            memebers2.put("6", 6.0);
            jedisClient.zadd("testKey2", memebers2);
            long result = jedisClient.zunionstore("testKey3", "testKey", "testKey2");
            Set<String> set = jedisClient.zrange(String.class, "testKey3", 0, -1);
            set.forEach(System.out::println);
            Assert.assertEquals(result, 6);

        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("testKey3");
        }
    }

    /*=============================sort set end========================================*/


    /*=============================hash start========================================*/
    /**
     * 测试存入hash
     * */
    @Test
    public void testHmset() throws IOException {
        try {
            jedisClient.del("testKey");
            Map<String, String> hash = new HashMap<>();
            hash.put("a", "1");
            hash.put("b", "2");
            boolean result = jedisClient.hmset("testKey", hash, 5);
            Assert.assertEquals(true, result);

        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取hash中所有的元素
     * */
    @Test
    public void testHgetAll() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            Map<String, String> hash = new HashMap<>();
            hash.put("a", "1");
            hash.put("b", "2");
            jedisClient.hmset("testKey", hash, 0);
            Map<String, String> result = jedisClient.hgetAll(String.class, String.class, "testKey");
            result.entrySet().forEach(System.out::println);
            Assert.assertEquals(2, result.size());

        } finally {
            jedisClient.del("testKey");
        }
    }
    /*=============================hash end  ========================================*/


}
