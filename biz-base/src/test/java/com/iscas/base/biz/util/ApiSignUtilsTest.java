package com.iscas.base.biz.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/2/21 14:24
 * @since jdk1.8
 */
public class ApiSignUtilsTest {
    private static final String secretKey = "ISCAS123";

    @Test
    public void test1() {
        Map signature = ApiSignUtils.getSignature(Map.of("username", "zhangsan", "password", "123456"), secretKey);
        System.out.println(signature);
    }


}