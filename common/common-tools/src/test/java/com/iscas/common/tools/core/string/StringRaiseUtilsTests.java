package com.iscas.common.tools.core.string;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/22 20:13
 * @since jdk1.8
 */
public class StringRaiseUtilsTests {
    @Test
    public void test() {
        String request_param = StringRaiseUtils.convertToHump("request_param");
        Assertions.assertEquals("requestParam", request_param);
    }

    @Test
    public void test2() {
        String request_param = StringRaiseUtils.convertToUnderline("requestParam");
        Assertions.assertEquals("request_param", request_param);
    }
}
