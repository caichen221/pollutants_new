package com.iscas.common.tools.core.collection;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/4/19 14:58
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class CollectionRaiseUtilsTests {
    @Test
    public void test() {
        ArrayList<Object> collection1 = CollectionUtil.newArrayList(null, null);
        boolean empty = CollectionRaiseUtils.isEmpty(collection1);
        Assert.assertTrue(empty);

        ArrayList<Object> collection2 = CollectionUtil.newArrayList("{test", null);
        boolean empty2 = CollectionRaiseUtils.isEmpty(collection2);
        Assert.assertFalse(empty2);
    }

    @Test
    public void test2() {
        Map<Object, Object> map1 = MapUtil.builder().put(null, null).build();
        boolean empty = MapRaiseUtils.isEmpty(map1);
        Assert.assertTrue(empty);

        Map<Object, Object> map2 = MapUtil.builder().put("key", "val").build();
        boolean empty2 = MapRaiseUtils.isEmpty(map2);
        Assert.assertFalse(empty2);
    }

    /**
     * 测试remove空值
     * */
    @Test
    public void test3() {
        Map<Object, Object> map = MapUtil.builder().put("name", "zhangsan")
                .put("age", 18)
                .put("lalala", null)
                .build();
        MapRaiseUtils.removeNullValue(map);
        Assert.assertEquals("{name=zhangsan, age=18}", map.toString());
        System.out.println(map);
    }
}
