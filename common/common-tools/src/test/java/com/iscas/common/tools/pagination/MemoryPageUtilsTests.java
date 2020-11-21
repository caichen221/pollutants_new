package com.iscas.common.tools.pagination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/4/12 14:24
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class MemoryPageUtilsTests {
    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> childList = MemoryPageUtils.getPageList(list, 2, 10);
        childList.stream().forEach(System.out::println);
    }
}
