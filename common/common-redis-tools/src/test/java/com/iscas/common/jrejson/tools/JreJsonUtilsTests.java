package com.iscas.common.jrejson.tools;

import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.RedisInfo;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/29 9:31
 * @since jdk1.8
 */
public class JreJsonUtilsTests {
    private JReJSON client = null;
    @BeforeEach
    public void before() {
        JedisConnection jedisConnection = new JedisStandAloneConnection();
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setMaxIdle(10);
        configInfo.setMaxTotal(50);
        configInfo.setMaxWait(20000);
        RedisInfo redisInfo = new RedisInfo();
        redisInfo.setHost("172.16.10.160");
        redisInfo.setPort(31158);
//        redisInfo.setPwd("iscas");
        redisInfo.setTimeout(10000);
        configInfo.setRedisInfos(Arrays.asList(redisInfo));
        client = JreJsonUtils.initJreJsonClient(jedisConnection, configInfo);
    }

    /**
     * 测试连接
     * */
    @Test
    public void testConnection() {
        System.out.println(client);
        Assertions.assertNotNull(client);
    }

    /**
     * 测试设置值
     * */
    @Test
    public void testSet() {
        client.set("key1", "中国");
        Assertions.assertEquals("中国", client.get("key1"));
    }

    /**
     * 测试path
     * */
    @Test
    public void testPath() {
        String val = client.get("key1", String.class, new Path("."));
        System.out.println(val);
        Assertions.assertEquals("中国", val);

        String val2 = client.get("key1", String.class, Path.ROOT_PATH);
        System.out.println(val2);
        Assertions.assertEquals("中国", val2);
    }

    /**
     * 测试path2
     * */
    @Test
    public void testPath2() {
        Map<String, String> map = Map.of("a", "我", "b", "网关");
        client.set("key2", map, Path.ROOT_PATH);
        System.out.println(client.get("key2", Map.class));
        System.out.println(client.get("key2", Map.class, Path.ROOT_PATH));
        System.out.println(client.get("key2", String.class, new Path(".a")));
        System.out.println(client.get("key2", String.class, new Path(".b")));

        //设置值
        client.set("key2", "啦啦啦", new Path(".b"));
        System.out.println(client.get("key2", String.class, new Path(".b")));

        //设置新值
        client.set("key2", "哈哈哈", new Path(".c"));
        System.out.println(client.get("key2", String.class, new Path(".c")));
        System.out.println(client.get("key2", Map.class, new Path(".")));

        //设置一个集合值
        client.set("key2", List.of(1, 2, 4, 5, 6), new Path(".d"));
        System.out.println(client.get("key2", Map.class, Path.ROOT_PATH));
        System.out.println(client.get("key2", List.class, new Path(".d")));
        System.out.println(client.get("key2", Integer.class, new Path(".d[0]")));

    }

}
