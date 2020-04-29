package com.iscas.common.tools.core.string;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/22 20:13
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class StringRaiseUtilsTests {
    @Test
    public void test() {
        String request_param = StringRaiseUtils.convertToHump("request_param");
        Assert.assertEquals("requestParam", request_param);
    }

    @Test
    public void test2() {
        String request_param = StringRaiseUtils.convertToUnderline("requestParam");
        Assert.assertEquals("request_param", request_param);
    }
}
