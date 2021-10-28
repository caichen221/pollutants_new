package com.iscas.common.tools.core.reflect.reflectTest;

import com.iscas.common.tools.core.reflect.ReflectUtils;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.text.MessageFormat;

/**
 * MethodHandle
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/27 18:03
 * @since jdk1.8
 */
public class MethodHandleTests {

    public void test(String name) {
        System.out.println(MessageFormat.format("你好 {0}", name));
    }

    @Test
    public void test() throws Throwable {
        MethodHandle methodHandle = ReflectUtils.getMethodHandle(void.class, MethodHandleTests.class, "test", String.class);
        methodHandle.invokeExact(new MethodHandleTests(), "张三");
    }
}
