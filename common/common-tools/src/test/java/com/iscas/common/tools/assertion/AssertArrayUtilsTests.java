package com.iscas.common.tools.assertion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 数组断言测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:59
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class AssertArrayUtilsTests {


    @Test
    public void test5() {
        Integer[] array = null;
        AssertArrayUtils.assertArrayNull(array, "数组必须为空");
    }

    @Test
    public void test6() {
        Integer[] array = null;
        AssertArrayUtils.assertArrayNotNull(array, "数组不能为空");
    }
}
