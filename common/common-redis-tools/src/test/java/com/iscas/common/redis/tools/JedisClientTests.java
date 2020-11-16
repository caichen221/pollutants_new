package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.impl.JedisClient;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
     * 测试Map 设置Map,类型为对象
     */
    @Test
    @Ignore
    public void test23() throws IOException {
        Map map = new HashMap<>();
        map.put("a", 11);
        map.put("b", 22);
        map.put("c", 33);
        boolean result = jedisClient.setMap("map2", map, 0);
        System.out.println(result);
    }

    /**
     * 获取Map 类型为对象
     */
    @Test
    @Ignore
    public void test25() throws IOException, ClassNotFoundException {
        Map<String, Object> map1 = jedisClient.getMap("map2");
        System.out.println(map1);
    }

    /**
     * 向map中添加数据， 类型为对象
     */
    @Test
    @Ignore
    public void test27() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("g", Arrays.asList(1, 2, 3, 4));
        boolean result = jedisClient.mapPut("map2", map);
        System.out.println(result);
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


}
