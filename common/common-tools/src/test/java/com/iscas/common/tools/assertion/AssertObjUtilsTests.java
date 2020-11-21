package com.iscas.common.tools.assertion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 对象断言测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 17:59
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class AssertObjUtilsTests {
    @Test
    public void test1() {
        AssertObjUtils.assertNotNull(null, "xxx不能为空");
    }

    @Test
    public void test2() {
        AssertObjUtils.assertNull(null, "xxx必须为空");
    }

    @Test
    public void test3() {
        Integer x = null;
        AssertObjUtils.assertNotEmpty(x, "xxx必须不为空");
    }


}
