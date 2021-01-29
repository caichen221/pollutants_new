package com.iscas.common.tools.core.io.serial;

import lombok.Cleanup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/29 8:19
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class JdkSerializableUtilsTests {
    @Test
    public void test() throws IOException, ClassNotFoundException {
        long begin = System.currentTimeMillis();
        byte[] bytes = null;
        for (int i = 0; i < 10000; i++) {

            Map<String,Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            Simple simple = new Simple("zhang" + i, (i + 1), map);
            bytes = JdkSerializableUtils.serialize(simple);
        }
        System.out.printf("序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        Simple simple = JdkSerializableUtils.deserialize(bytes);
        System.out.printf("反序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {
        long begin = System.currentTimeMillis();
        @Cleanup OutputStream os = new FileOutputStream("d:/mytest/jdk.bin");
        @Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        for (int i = 0; i < 10000; i++) {

            Map<String,Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            Simple simple = new Simple("zhang" + i, (i + 1), map);
            JdkSerializableUtils.serialize(simple, objectOutputStream);
        }
        System.out.printf("序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        @Cleanup InputStream in = new FileInputStream("d:/mytest/jdk.bin");
        @Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(in);
        Simple simple = JdkSerializableUtils.deserialize(objectInputStream);
        System.out.printf("反序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
    }
}
