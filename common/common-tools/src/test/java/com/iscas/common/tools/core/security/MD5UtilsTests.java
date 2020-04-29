package com.iscas.common.tools.core.security;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类测试
 * @author zhuquanwen
 **/

public class MD5UtilsTests {
    /**
     * 普通加密
     * */
    @Test
    public void MD51() throws NoSuchAlgorithmException {
        String sec = MD5Utils.md5("apply");
        Assert.assertNotNull(sec);
    }
    /**
     * 加盐加密
     * */
    @Test
    public void MD52() throws NoSuchAlgorithmException {
        String sec = MD5Utils.saltMD5("admin");
        System.out.println(sec);
        Assert.assertNotNull(sec);
    }
    /**
     * 加盐加密的校验
     * */
    @Test
    public void MD53() throws NoSuchAlgorithmException {
        boolean sec = MD5Utils.saltVerify("admin"
                ,"09e480814288b5245d39228681f23f311208e9a57170a990");
        Assert.assertEquals(true, sec);
    }

    /**
     * 获取大文件MD5码
     * */
    @Test
    public void testFileMD5() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        String fileMD5 = MD5Utils.getFileMD5(new FileInputStream("G:/cs-video/dist/fe8fc15215877f8422c43dfa408c1518.store"));
        Assert.assertNotNull(fileMD5);
        System.out.println(fileMD5);
        long end = System.currentTimeMillis();
        System.out.printf("耗时：%dms", (end - start));
    }
}
