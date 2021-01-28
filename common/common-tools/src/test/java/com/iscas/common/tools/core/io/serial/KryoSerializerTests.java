package com.iscas.common.tools.core.io.serial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

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
//        IKryoSerializer ser = new KryoSerializer(Msg.class);
        for (int i = 0; i < 10; i++) {

            Msg msg = new Msg();

            msg.setVersion_flag(new byte[]{1, 2, 3});
            msg.setCrc_code((short) 1);
            msg.setMsg_body(new byte[]{123, 123, 123, 43, 42, 1, 12, 45, 57, 98});
            long start = System.nanoTime();
            byte[] bytes = KryoSerializerUtils.serialize(msg);
            System.err.println("序列化耗时：" + (System.nanoTime() - start));
            System.out.println(msg);
            System.out.println(Arrays.toString(bytes));

            Msg newmsg = null;
            start = System.nanoTime();
            newmsg = KryoSerializerUtils.deserialize(bytes, Msg.class);
            System.err.println("反序列化耗时：" + (System.nanoTime() - start));
            System.out.println(newmsg);
        }
    }


}
