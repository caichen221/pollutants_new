package com.iscas.common.redis.tools;

import com.iscas.common.redis.tools.impl.JedisClient;
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

    /**
     * 测试插入一个对象，有超时时间
     * */
    @Test
    @Ignore
    public void test3() throws IOException {
        boolean result = jedisClient.set("t2", "value", 20);
        Assert.assertTrue(result);
    }

    /**
     * 测试获取对象
     * */
    @Test
    @Ignore
    public void test5() throws IOException, ClassNotFoundException {
        Object result = jedisClient.get(Object.class, "t2");
        Assert.assertTrue(result instanceof String);
        System.out.println(result);
    }

    /**
     * 测试设置List数据,数据为对象
     * */
    @Test
    @Ignore
    public void test7() throws IOException {
        long list1 = jedisClient.setList("list2", Arrays.asList("4", "5", "6"), 100);
        System.out.println(list1);
    }

    /**
     * 测试向List添加数据,数据为对象
     * */
    @Test
    @Ignore
    public void test9() throws IOException {
        long list1 = jedisClient.listAdd("list2", "x", "y", "z");
        System.out.println(list1);
    }

    /**
     * 测试从List获取数据,数据为对象
     * */
    @Test
    @Ignore
    public void test11() throws IOException, ClassNotFoundException {
        List<Object> list1 = jedisClient.getList("list2");
        list1.stream().forEach(System.out::println);
    }

    /**
     * 测试从List pop数据,数据为对象，适用于队列模式
     * */
    @Test
    @Ignore
    public void test13() throws IOException, ClassNotFoundException {
        Object result = jedisClient.lpopList("list2");
        System.out.println(result);
    }

    /**
     * 测试从List pop数据,数据为对象，适用于队列模式
     * */
    @Test
    @Ignore
    public void test15() throws IOException, ClassNotFoundException {
        Object result = jedisClient.rpopList("list2");
        System.out.println(result);
    }

    /**
     * 测试设置集合，数据为对象
     * */
    @Test
    @Ignore
    public void test17() throws IOException {
        Set<Object> set = new HashSet<>();
        set.add("x");
        set.add("y");
        set.add("z");
        set.add("m");
        long result = jedisClient.setSet("set2", set, 0);
        System.out.println(result);
    }

    /**
     * 测试集合追加值，数据为对象
     * */
    @Test
    @Ignore
    public void test19() throws IOException {
        long result = jedisClient.setSetAdd("set2", "x", "p", "q");
        System.out.println(result);
    }

    /**
     * 测试集合获取数据,获取对象数据
     * */
    @Test
    @Ignore
    public void test21() throws IOException, ClassNotFoundException {
        Set<Object> set = jedisClient.getSet("set2");
        set.stream().forEach(System.out::println);
    }

    /**
     * 测试Map 设置Map,类型为对象
     * */
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
     * */
    @Test
    @Ignore
    public void test25() throws IOException, ClassNotFoundException {
        Map<String, Object> map1 = jedisClient.getMap("map2");
        System.out.println(map1);
    }

    /**
     * 向map中添加数据， 类型为对象
     * */
    @Test
    @Ignore
    public void test27() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("g", Arrays.asList(1,2,3,4));
        boolean result = jedisClient.mapPut("map2", map);
        System.out.println(result);
    }

    /**
     * 移除某个Map值,类型为对象
     * */
    @Test
    @Ignore
    public void test29() throws IOException {
        long l = jedisClient.mapRemove("map2", "g");
        System.out.println(l);
    }

    /**
     * 判断Map中某个值是否存在
     * */
    @Test
    @Ignore
    public void test31() throws IOException {
        boolean b = jedisClient.mapExists("map2", "a");
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
     * 测试交集
     * */
    @Test
    public void test42() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "22222", "33333", 45);
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "22222", "44444", 45);
            Set<Object> result = jedisClient.sinter("testKey", "testKey2");
            result.stream().forEach(System.out::println);
            Assert.assertEquals(3, result.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试交集,存入新集合
     * */
    @Test
    public void test44() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333", 67);
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "44444", 67);
            long result = jedisClient.sinterStore("newKey","testKey", "testKey2");
            Set<Object> newSet = jedisClient.getSet("newKey");
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
     * */
    @Test
    public void test46() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333", 1111);
            boolean exists = jedisClient.sismember("testKey", 1111);
            Assert.assertEquals(true, exists);
        } finally {
            jedisClient.del("testKey");
        }
    }


    /**
     * 测试获取集合中所有的元素,集合中存储为对象
     * */
    @Test
    public void test48() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333", 23455);
            Set<Object> members = jedisClient.smembers("testKey");
            members.stream().forEach(System.out::println);
            Assert.assertEquals(3, members.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中移除对象成员,放入目标集合
     * */
    @Test
    public void test50() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
            jedisClient.setSetAdd("srcKey", "11111", "33333");
            jedisClient.setSetAdd("dstKey", "2222", "5555");
            jedisClient.smove("srcKey", "dstKey", "11111");
            Set<Object> srcSet = jedisClient.smembers("srcKey");
            Set<Object> dstSet = jedisClient.smembers("dstKey");
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
     * */
    @Test
    public void test52() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            Object data = jedisClient.spop("testKey");
            System.out.println(data);
            Assert.assertEquals(1, jedisClient.scard("testKey"));
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取集合中对象元素的个数
     * */
    @Test
    public void test53() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", 2, 3, 4);
            long sum = jedisClient.scard("testKey");
            Assert.assertEquals(3, sum);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试设置存储对象的key的过期时间
     * */
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


    /**
     * 测试从集合中随机移除对象成员并返回
     * */
    @Test
    public void test56() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333", 3);
            Set<Object> datas = jedisClient.spop("testKey", 3);
            datas.forEach(System.out::println);
            Assert.assertEquals(3, datas.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中删除成员
     * */
    @Test
    public void test57() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", 33333);
            long result = jedisClient.srem("testKey", "11111", "44444");
            Set<Object> set = jedisClient.smembers("testKey");
            set.forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
        }
    }


}
