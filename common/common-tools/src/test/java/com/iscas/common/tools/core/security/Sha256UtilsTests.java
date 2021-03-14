package com.iscas.common.tools.core.security;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/14 13:36
 * @since jdk1.8
 */
public class Sha256UtilsTests {

    @Test
    public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String sha256Str = Sha256Utils.getSHA256Str("123456");
        System.out.println(sha256Str);
    }
}
