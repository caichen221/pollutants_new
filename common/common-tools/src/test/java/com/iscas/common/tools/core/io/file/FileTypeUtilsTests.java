package com.iscas.common.tools.core.io.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * 判断文件的类型
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/8/6 9:11
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class FileTypeUtilsTests {
    @Test
    public void test() throws IOException {
        FileTypeEnum fileType = FileTypeUtils.getFileType("e:/test.jpg");
        System.out.println(fileType);

    }
}
