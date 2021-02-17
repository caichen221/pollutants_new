package com.iscas.common.tools.exception;

import com.iscas.common.tools.exception.lambda.LambdaExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/17 14:19
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class LambdaExceptionUtilsTests {
    private List<String> list = Arrays.asList("1", "2", "0", "4", "8x");

    @Test
    public void test0() {
        list.stream().forEach(i -> System.out.println((100 / Integer.parseInt(i))));
    }

//    @Test
//    public void test1() {
//        list.stream().forEach(LambdaExceptionUtils.lambdaRuntimeWrapper(i -> System.out.println((100 / Integer.parseInt(i)))));
//    }

    @Test
    public void test2() {
        list.stream().forEach(i -> {
            File file = new File(i);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void test3() {
        list.stream().forEach(LambdaExceptionUtils.lambdaWrapper(i -> {
            File file = new File(i);
            FileInputStream fileInputStream = new FileInputStream(file);
        }));
    }

    @Test
    public void test4() {
        list.stream().map(LambdaExceptionUtils.lambdaWrapper(i -> "lalala" + Integer.parseInt(i)))
                .forEach(System.out::println);
    }

    @Test
    public void test5() {
        list.stream().filter(LambdaExceptionUtils.lambdaPredicateWrapper(i -> i.length() == 1))
                .forEach(System.out::println);
    }
}
