package com.iscas.common.aspose.tools;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 12:39
 * @since jdk11
 */
class ConvertUtilsTest {

    @Test
    public void test() throws Exception {
        try (
                InputStream is = Files.newInputStream(Paths.get("C:\\Users\\Administrator\\Desktop\\新建 DOCX 文档.docx"));
                OutputStream os =Files.newOutputStream(Paths.get("C:\\Users\\Administrator\\Desktop\\test.pdf"))
        ) {
            ConvertUtils.doc2pdf(is, os);
        }
    }
}