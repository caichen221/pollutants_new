package com.iscas.common.tools.arithmetic;

import com.iscas.common.tools.core.arithmetic.FloatExactArithUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

/**
 * 浮点精确计算工具类测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/8/11 21:05
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
@Slf4j
public class FloatExactArithUtilsTests {

    @Test
    public void testAdd() {
        double data1 = 0.01;
        double data2 = 0.05;
        log.debug(String.format("不精确计算的结果：%f", (data1 + data2)));
        double result = FloatExactArithUtils.add(data1, data2);
        log.debug(String.format("精确计算的结果：%f", result));
        Assert.assertEquals(0.06, result, 0);
    }

    @Test
    public void testSub() {
        double data1 = 0.051;
        double data2 = 0.01;
        log.debug(String.format("不精确计算的结果：%f", (data1 - data2)));
        double result = FloatExactArithUtils.subtract(data1, data2);
        log.debug(String.format("精确计算的结果：%f", result));
        Assert.assertEquals(0.041, result, 0);
    }

    @Test
    public void testMultiply() {
        double data1 = 0.051;
        double data2 = 0.01;
        log.debug(String.format("不精确计算的结果：%f", (data1 * data2)));
        double result = FloatExactArithUtils.multiply(data1, data2);
        log.debug(String.format("精确计算的结果：%f", result));
        Assert.assertEquals(0.00051, result, 0);
    }

    @Test
    public void testDivide() {
        double data1 = 0.051;
        double data2 = 0.01;
        log.debug(String.format("不精确计算的结果：%f", (data1 / data2)));
        double result = FloatExactArithUtils.divide(data1, data2, 1);
        log.debug(String.format("精确计算的结果：%f", result));
        Assert.assertEquals(5.1, result, 0);
    }

    @Test
    public void testBigDecimal() {
        double d = 0.999;
        BigDecimal b1 = BigDecimal.valueOf(d);
        BigDecimal b2 = new BigDecimal("0.999");
        BigDecimal b3 = new BigDecimal(d);
        System.out.printf("BigDecimal.valueOf(double d)的结果：%s\n", b1.toString() );
        System.out.printf("new BigDecimal(String d)的结果：%s\n", b2.toString() );
        System.out.printf("new BigDecimal(double d)的结果：%s\n", b3.toString() );
    }
}
