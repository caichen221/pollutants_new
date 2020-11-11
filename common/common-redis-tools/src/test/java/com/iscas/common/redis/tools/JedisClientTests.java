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
     * 测试插入一个对象，有超时时间
     * */
    @Test
    @Ignore
    public void test3() throws IOException {
        boolean result = jedisClient.setObject("t2", "value", 20);
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
     * 测试获取对象
     * */
    @Test
    @Ignore
    public void test5() throws IOException, ClassNotFoundException {
        Object result = jedisClient.getObject("t2");
        Assert.assertTrue(result instanceof String);
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
     * 测试设置List数据,数据为对象
     * */
    @Test
    @Ignore
    public void test7() throws IOException {
        long list1 = jedisClient.setObjectList("list2", Arrays.asList("4", "5", "6"), 100);
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
     * 测试向List添加数据,数据为对象
     * */
    @Test
    @Ignore
    public void test9() throws IOException {
        long list1 = jedisClient.listObjectAdd("list2", "x", "y", "z");
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
     * 测试从List获取数据,数据为对象
     * */
    @Test
    @Ignore
    public void test11() throws IOException, ClassNotFoundException {
        List<Object> list1 = jedisClient.getObjectList("list2");
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
     * 测试从List pop数据,数据为对象，适用于队列模式
     * */
    @Test
    @Ignore
    public void test13() throws IOException, ClassNotFoundException {
        Object result = jedisClient.lpopObjectList("list2");
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
     * 测试从List pop数据,数据为对象，适用于队列模式
     * */
    @Test
    @Ignore
    public void test15() throws IOException, ClassNotFoundException {
        Object result = jedisClient.rpopObjectList("list2");
        System.out.println(result);
    }

    /**
     * 测试设置集合，数据为字符串
     * */
    @Test
    @Ignore
    public void test16() {
        Set<String> set = new HashSet<>();
        set.add("x");
        set.add("y");
        set.add("z");
        set.add("m");
        long result = jedisClient.setSet("set1", set, 0);
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
        long result = jedisClient.setObjectSet("set2", set, 0);
        System.out.println(result);
    }

    /**
     * 测试集合追加值，数据为字符串
     * */
    @Test
    @Ignore
    public void test18()  {
        long result = jedisClient.setSetAdd("set1", "x", "p", "q");
        System.out.println(result);
    }

    /**
     * 测试集合追加值，数据为对象
     * */
    @Test
    @Ignore
    public void test19() throws IOException {
        long result = jedisClient.setSetObjectAdd("set2", "x", "p", "q");
        System.out.println(result);
    }

    /**
     * 测试集合获取数据
     * */
    @Test
    @Ignore
    public void test20() {
        Set<String> set = jedisClient.getSet("set1");
        set.stream().forEach(System.out::println);
    }

    /**
     * 测试集合获取数据,获取对象数据
     * */
    @Test
    @Ignore
    public void test21() throws IOException, ClassNotFoundException {
        Set<Object> set = jedisClient.getObjectSet("set2");
        set.stream().forEach(System.out::println);
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
     * 测试Map 设置Map,类型为对象
     * */
    @Test
    @Ignore
    public void test23() throws IOException {
        Map map = new HashMap<>();
        map.put("a", 11);
        map.put("b", 22);
        map.put("c", 33);
        boolean result = jedisClient.setObjectMap("map2", map, 0);
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
     * 获取Map 类型为对象
     * */
    @Test
    @Ignore
    public void test25() throws IOException, ClassNotFoundException {
        Map<String, Object> map1 = jedisClient.getObjectMap("map2");
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
     * 向map中添加数据， 类型为对象
     * */
    @Test
    @Ignore
    public void test27() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("g", Arrays.asList(1,2,3,4));
        boolean result = jedisClient.mapObjectPut("map2", map);
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
     * 移除某个Map值,类型为对象
     * */
    @Test
    @Ignore
    public void test29() throws IOException {
        long l = jedisClient.mapObjectRemove("map2", "g");
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
     * 判断Map中某个值是否存在
     * */
    @Test
    @Ignore
    public void test31() throws IOException {
        boolean b = jedisClient.mapObjectExists("map2", "a");
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
     * 测试获取集合中元素的个数
     * */
    @Test
    public void test35() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", 2, 3, 4);
            long sum = jedisClient.scard("testKey");
            Assert.assertEquals(3, sum);
        } finally {
            jedisClient.del("testKey");
        }
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

    /**
     * 测试差集
     * */
    @Test
    public void test37() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "22222", "44444");
            Set<String> diffSet = jedisClient.sdiff("testKey", "testKey2");
            diffSet.forEach(System.out::println);
            Assert.assertEquals(1, diffSet.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试对象差集
     * */
    @Test
    public void test38() throws IOException, ClassNotFoundException {
        try {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("a", "xxxx");
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "22222", objectObjectHashMap);
            jedisClient.del("testKey2");
            jedisClient.setSetObjectAdd("testKey2", "11111", "22222", "44444");
            Set<Object> diffSet = jedisClient.sdiffObject("testKey", "testKey2");
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
    public void test39() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "22222", "44444");
            long result = jedisClient.sdiffStore("newKey", "testKey", "testKey2");
            System.out.println(result);
            Set<String> newSet = jedisClient.getSet("newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("newKey");
        }
    }

    /**
     * 测试差集，存储到新集合中
     * */
    @Test
    public void test40() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "22222", "33333", 123);
            jedisClient.del("testKey2");
            jedisClient.setSetObjectAdd("testKey2", "11111", "22222", "44444", 566);
            long result = jedisClient.sdiffObjectStore("newKey", "testKey", "testKey2");
            System.out.println(result);
            Set<Object> newSet = jedisClient.getObjectSet("newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(2, result);
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
    public void test41() throws IOException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "22222", "33333");
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "22222", "44444");
            Set<String> result = jedisClient.sinter("testKey", "testKey2");
            result.stream().forEach(System.out::println);
            Assert.assertEquals(2, result.size());
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
        }
    }

    /**
     * 测试交集
     * */
    @Test
    public void test42() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "22222", "33333", 45);
            jedisClient.del("testKey2");
            jedisClient.setSetObjectAdd("testKey2", "11111", "22222", "44444", 45);
            Set<Object> result = jedisClient.sinterObject("testKey", "testKey2");
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
    public void test43() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            jedisClient.del("testKey2");
            jedisClient.setSetAdd("testKey2", "11111", "44444");
            long result = jedisClient.sinterStore("newKey","testKey", "testKey2");
            Set<String> newSet = jedisClient.getSet("newKey");
            newSet.stream().forEach(System.out::println);
            Assert.assertEquals(1, result);
        } finally {
            jedisClient.del("testKey");
            jedisClient.del("testKey2");
            jedisClient.del("newKey");
        }
    }

    /**
     * 测试交集,存入新集合
     * */
    @Test
    public void test44() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "33333", 67);
            jedisClient.del("testKey2");
            jedisClient.setSetObjectAdd("testKey2", "11111", "44444", 67);
            long result = jedisClient.sinterObjectStore("newKey","testKey", "testKey2");
            Set<Object> newSet = jedisClient.getObjectSet("newKey");
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
    public void test45() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            boolean exists = jedisClient.sismember("testKey", "11111");
            Assert.assertEquals(true, exists);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试集合中某个元素是否存在
     * */
    @Test
    public void test46() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "33333", 1111);
            boolean exists = jedisClient.sismemberObject("testKey", 1111);
            Assert.assertEquals(true, exists);
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试获取集合中所有的元素
     * */
    @Test
    public void test47() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            Set<String> members = jedisClient.smembers("testKey");
            members.stream().forEach(System.out::println);
            Assert.assertEquals(2, members.size());
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
            jedisClient.setSetObjectAdd("testKey", "11111", "33333", 23455);
            Set<Object> members = jedisClient.smembersObject("testKey");
            members.stream().forEach(System.out::println);
            Assert.assertEquals(3, members.size());
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中移除成员,放入目标集合
     * */
    @Test
    public void test49() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
            jedisClient.setSetAdd("srcKey", "11111", "33333");
            jedisClient.setSetAdd("dstKey", "2222", "5555");
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
     * 测试从集合中移除对象成员,放入目标集合
     * */
    @Test
    public void test50() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("srcKey");
            jedisClient.del("dstKey");
            jedisClient.setSetObjectAdd("srcKey", "11111", "33333");
            jedisClient.setSetObjectAdd("dstKey", "2222", "5555");
            jedisClient.smoveObject("srcKey", "dstKey", "11111");
            Set<Object> srcSet = jedisClient.smembersObject("srcKey");
            Set<Object> dstSet = jedisClient.smembersObject("dstKey");
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
    public void test51() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            String data = jedisClient.spop("testKey");
            System.out.println(data);
            Assert.assertEquals(1, jedisClient.scard("testKey"));
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中随机移除对象成员并返回
     * */
    @Test
    public void test52() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetObjectAdd("testKey", "11111", "33333");
            Object data = jedisClient.spopObject("testKey");
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
            jedisClient.setSetObjectAdd("testKey", 2, 3, 4);
            long sum = jedisClient.scardObject("testKey");
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
            jedisClient.setObject("testKey", "11111", 0);
            jedisClient.expireObject("testKey", 5000);
            Assert.assertEquals("11111", jedisClient.getObject("testKey"));
            TimeUnit.SECONDS.sleep(6);
            Assert.assertNull(jedisClient.getObject("testKey"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            jedisClient.del("testKey");
        }
    }

    /**
     * 测试从集合中随机移除成员多个并返回
     * */
    @Test
    public void test55() throws IOException, ClassNotFoundException {
        try {
            jedisClient.del("testKey");
            jedisClient.setSetAdd("testKey", "11111", "33333");
            Set<String> datas = jedisClient.spop("testKey", 3);
            datas.forEach(System.out::println);
            Assert.assertEquals(2, datas.size());
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
            jedisClient.setSetObjectAdd("testKey", "11111", "33333", 3);
            Set<Object> datas = jedisClient.spopObject("testKey", 3);
            datas.forEach(System.out::println);
            Assert.assertEquals(3, datas.size());
        } finally {
            jedisClient.del("testKey");
        }
    }


}
