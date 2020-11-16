package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.impl.JedisStrClient;
import com.iscas.common.redis.tools.impl.cluster.JedisClusterConnection;
import com.iscas.common.redis.tools.impl.shard.JedisShardConnection;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
public class JedisClientStrTests {
    private IJedisStrClient jedisClient;
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
        jedisClient = new JedisStrClient(jedisConnection, configInfo);

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

    /*=====================================测试SET BEGIN===================================================*/
    /**
     * 测试设置集合，数据为字符串
     * */
    @Test
    @Ignore
    public void testAdd() {
        try {
            jedisClient.del("set1");
            Set<String> set = new HashSet<>();
            set.add("x");
            set.add("y");
            set.add("z");
            set.add("m");
            long result = jedisClient.sadd("set1", set, 0);
            System.out.println(result);
        } finally {
            jedisClient.del("set1");
        }
    }

    /**
     * 测试集合追加值，数据为字符串
     * */
    @Test
    @Ignore
    public void testAdd2()  {
        try {
            jedisClient.del("set1");
            long result = jedisClient.sadd("set1", "x", "p", "q");
            System.out.println(result);
        } finally {
            jedisClient.del("set1");
        }
    }

    /**
     * 测试差集
     * */
    @Test
    public void testSdiff() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "22222", "44444");
            Set<String> diffSet = jedisClient.sdiff("testKey", "testKey2");
            diffSet.forEach(System.out::println);
            Assert.assertEquals(1, diffSet.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试差集，存储到新集合中
     * */
    @Test
    public void testSdiffStore() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "22222", "44444");
            long result = jedisClient.sdiffStore("newKey", "testKey", "testKey2");
            System.out.println(result);
            Set<String> newSet = jedisClient.smembers("newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("newKey");
        }
    }

    /**
     * 测试交集
     * */
    @Test
    public void testSinter() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "22222", "44444");
            Set<String> result = jedisClient.sinter("testKey", "testKey2");
            result.stream().forEach(System.out::println);
            Assert.assertEquals(2, result.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试交集,存入新集合
     * */
    @Test
    public void testSinterStore() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey2", "11111", "44444");
            long result = jedisClient.sinterStore("newKey","testKey", "testKey2");
            Set<String> newSet = jedisClient.smembers("newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("newKey");
        }
    }

    /**
     * 测试集合中某个元素是否存在
     * */
    @Test
    public void testSismember() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            boolean exists = jedisClient.sismember("testKey", "11111");
            Assert.assertEquals(true, exists);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取集合中所有的元素
     * */
    @Test
    public void testSmembers() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            Set<String> members = jedisClient.smembers("testKey");
            members.stream().forEach(System.out::println);
            Assert.assertEquals(2, members.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中移除成员,放入目标集合
     * */
    @Test
    public void testSmove() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
            jedisClient.sadd("srcKey", "11111", "33333");
            jedisClient.sadd("dstKey", "2222", "5555");
            jedisClient.smove("srcKey", "dstKey", "11111");
            Set<String> srcSet = jedisClient.smembers("srcKey");
            Set<String> dstSet = jedisClient.smembers("dstKey");
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
     * 测试从集合中随机移除成员并返回
     * */
    @Test
    public void testScard() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            String data = jedisClient.spop("testKey");
            System.out.println(data);
            Assert.assertEquals(1, jedisClient.scard("testKey"));
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中随机移除成员多个并返回
     * */
    @Test
    public void testSpop() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            Set<String> datas = jedisClient.spop("testKey", 3);
            datas.forEach(System.out::println);
            Assert.assertEquals(2, datas.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中删除成员
     * */
    @Test
    public void testSrem() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.sadd("testKey", "11111", "33333");
            long result = jedisClient.srem("testKey", "11111", "44444");
            Set<String> set = jedisClient.smembers("testKey");
            set.forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试去并集
     * */
    @Test
    public void testSunion() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.sadd("testKey", "1", "2", "3");
            jedisClient.sadd("testKey2", "1", "2", "3", "7");
            Set<String> sunionSet = jedisClient.sunion("testKey", "testKey2");
            sunionSet.forEach(System.out::println);
            Assert.assertEquals(4, sunionSet.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /*=====================================测试SET END  ===================================================*/

    /**
     * 测试插入一个字符串，无超时时间
     * */
    @Test
    @Ignore
    public void test1() {
        boolean result = jedisClient.set("t1", "value", 0);
        Assert.assertTrue(result);
    }

    /**
     * 测试插入一个字符串，有超时时间
     * */
    @Test
    @Ignore
    public void test2() {
        boolean result = jedisClient.set("t1", "value", 20);
        Assert.assertTrue(result);
    }


    /**
     * 测试获取字符串
     * */
    @Test
    @Ignore
    public void test4() {
        String result = jedisClient.get("t1");
        System.out.println(result);
    }



    /**
     * 测试设置List数据
     * */
    @Test
    @Ignore
    public void test6() {
        long list1 = jedisClient.setList("list1", Arrays.asList("4", "5", "6"), 100);
        System.out.println(list1);
    }

    /**
     * 测试向List添加数据,数据为字符串
     * */
    @Test
    @Ignore
    public void test8() {
        long list1 = jedisClient.listAdd("list1", "x", "y", "z");
        System.out.println(list1);
    }

    /**
     * 测试从List获取数据,数据为字符串
     * */
    @Test
    @Ignore
    public void test10() {
        List<String> list1 = jedisClient.getList("list1");
        list1.stream().forEach(System.out::println);
    }

    /**
     * 测试从List pop数据,数据为字符串，适用于队列模式
     * */
    @Test
    @Ignore
    public void test12(){
        String result = jedisClient.lpopList("list1");
        System.out.println(result);
    }

    /**
     * 测试从List pop数据,数据为字符串，适用于栈模式
     * */
    @Test
    @Ignore
    public void test14(){
        String result = jedisClient.rpopList("list1");
        System.out.println(result);
    }



    /**
     * 测试Map 设置Map,类型为字符串
     * */
    @Test
    @Ignore
    public void test22() {
        Map map = new HashMap<>();
        map.put("a", "11");
        map.put("b", "22");
        map.put("c", "33");
        boolean result = jedisClient.setMap("map1", map, 0);
        System.out.println(result);
    }

    /**
     * 获取Map 类型为字符串
     * */
    @Test
    @Ignore
    public void test24() {
        Map<String, String> map1 = jedisClient.getMap("map1");
        System.out.println(map1);
    }

    /**
     * 向map中添加数据
     * */
    @Test
    @Ignore
    public void test26() throws IOException, ClassNotFoundException {
        Map<String, String> map = new HashMap<>();
        map.put("f", "F");
        boolean result = jedisClient.mapPut("map1", map);
        System.out.println(result);
    }

    /**
     * 移除某个Map值
     * */
    @Test
    @Ignore
    public void test28() {
        long l = jedisClient.mapRemove("map1", "f");
        System.out.println(l);
    }

    /**
     * 判断Map中某个值是否存在
     * */
    @Test
    @Ignore
    public void test30(){
        boolean b = jedisClient.mapExists("map1", "g");
        System.out.println(b);
    }

    /**
     * 测试分布式锁
     * */
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
        for (int i = 0; i < 10 ; i++) {
            executorService.submit(runnable);
            countDownLatch.countDown();
        }
//        // 假设一个足够长的时间去验证最后的goodCount
        for (int i = 0; i < 10 ; i++) {
            System.out.println(goodsCount);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * 测试分布式限流
     * */
    @Test
    @Ignore
    public void test33() {
        for (int i = 0; i < 150 ; i++) {
            boolean flag = jedisClient.accessLimit("localhost", 10, 100);
            System.out.println(flag);
        }

    }

    /**
     * 测试延时队列
     * */
    @Test
    public void test34() {
        jedisClient.putDelayQueue("this is test", 5, TimeUnit.SECONDS, (task)-> {
            System.out.println(task);
        });
    }

    /**
     * 测试设置key的过期时间
     * */
    @Test
    public void test36() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.set("testKey", "11111", 0);
            jedisClient.expire("testKey", 5000);
            Assert.assertEquals("11111", jedisClient.get("testKey"));
            TimeUnit.SECONDS.sleep(6);
            Assert.assertNull(jedisClient.get("testKey"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            jedisClient.del("testKey");
        }
    }



}
