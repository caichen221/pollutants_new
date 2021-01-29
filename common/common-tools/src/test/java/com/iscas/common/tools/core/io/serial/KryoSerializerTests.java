package com.iscas.common.tools.core.io.serial;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.Cleanup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 21:33
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class KryoSerializerTests {
    @Test
    public void test() {
        long begin = System.currentTimeMillis();
        byte[] bytes = null;
        for (int i = 0; i < 10000; i++) {
            Map<String,Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            Simple simple = new Simple("zhang" + i, (i + 1), map);
            bytes = KryoSerializerUtils.serialize(simple);
        }
        System.out.printf("序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        Simple simple = KryoSerializerUtils.deserialize(bytes, Simple.class);
        System.out.printf("反序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
    }

    @Test
    public void test2() throws IOException {
        long begin = System.currentTimeMillis();
        @Cleanup OutputStream os = new FileOutputStream("d:/mytest/kryo.bin");
        @Cleanup Output output = new Output(os);
        for (int i = 0; i < 10000; i++) {
            Map<String,Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            Simple simple = new Simple("zhang" + i, (i + 1), map);
            KryoSerializerUtils.serialize(simple, output);
            Simple newmsg = null;
        }
        System.out.printf("序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        @Cleanup InputStream is = new FileInputStream("d:/mytest/kryo.bin");
        @Cleanup Input input = new Input(is);
        Simple simple = KryoSerializerUtils.deserialize(input, Simple.class);
        System.out.printf("反序列化总计耗时:[%d]\n", (System.currentTimeMillis() - begin));
    }


}
