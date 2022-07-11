package com.iscas.common.aspose.tools;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 12:39
 * @since jdk11
 */
class ConvertUtilsTest {

    /**
     * 测试word转为PDF
     * */
    @Test
    public void testWord2Pdf() throws Exception {
        try (
                InputStream is = Files.newInputStream(Paths.get("D:\\文档资料\\_部署安装\\离线安装k8s\\1、安装k8s集群\\离线安装k8s集群.docx"));
                OutputStream os =Files.newOutputStream(Paths.get("D:/tmp/test.pdf"))
        ) {
            ConvertUtils.word2pdf(is, os);
        }
    }

    /**
     * 测试word转为HTML
     * */
    @Test
    public void testWord2Html() throws Exception {
        try (
                InputStream is = Files.newInputStream(Paths.get("D:\\文档资料\\_部署安装\\离线安装k8s\\1、安装k8s集群\\离线安装k8s集群.docx"));
                OutputStream os =Files.newOutputStream(Paths.get("D:/tmp/test.html"))
        ) {
            ConvertUtils.word2html(is, os);
        }
    }

    /**
     * 测试HTML转为word
     * */
    @Test
    public void testHtml2Docx() throws Exception {
        try (
                InputStream is = Files.newInputStream(Paths.get("D:/tmp/test.html"));
                OutputStream os =Files.newOutputStream(Paths.get("D:/tmp/test.docx"))
        ) {
            ConvertUtils.html2docx(new String(is.readAllBytes(), StandardCharsets.UTF_8), os);
        }
    }

}