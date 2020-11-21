package com.iscas.common.tools.core.io.zip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

/**
 * zip工具测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/4/30 13:59
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class ZipUtilsTests {

    /**
     * 压缩
     * */
    @Test
//    @Ignore
    public void test() throws Exception {
        long start = System.currentTimeMillis();
        ZipUtils.toZip("f:/es", "f:/testZip", "es");
        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "毫秒");
    }

    /**
     * 解压缩
     * */
    @Test
    public void test2() throws Exception {
        long start = System.currentTimeMillis();
        ZipUtils.unZip(new File("f:/testZip/es.zip"), "f:/testZip2");
        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "毫秒");
    }
}
