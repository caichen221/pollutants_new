package com.iscas.common.aspose.tools;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/11 14:52
 * @since jdk11
 */
class WordOperateUtilsTest {

    /**
     * 测试拼接一个word文档
     */
    @Test
    public void testAppendDoc() throws Exception {
        LicenseUtils.initLicense();
        Document doc1 = new Document("D:/文档资料/安全认证.docx");
        Document doc2 = new Document("D:/文档资料/智能门户.docx");
        doc1 = WordOperateUtils.appendDoc(doc1, doc2, true, null);
        doc1.save(Files.newOutputStream(Paths.get("d:/tmp/智能menhu.docx")), SaveFormat.DOCX);
    }

    /**
     * 测试拼接一个word文档
     */
    @Test
    public void testAppendDoc2() throws Exception {
        try (
                InputStream mainIs = Files.newInputStream(Paths.get("D:/文档资料/安全认证.docx"));
                InputStream addIs = Files.newInputStream(Paths.get("D:/文档资料/智能门户.docx"));
                OutputStream os = Files.newOutputStream(Paths.get("D:/文档资料/智能menhu.docx"));
                ) {
            WordOperateUtils.appendDoc(mainIs, addIs, os, SaveFormat.DOCX, true, null);
        }
    }
}