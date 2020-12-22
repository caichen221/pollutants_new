package com.iscas.common.tools.pdfword.linux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * linux下word转PDF测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/22 18:07
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class LinuxWord2PDFTests {

    @Test
    public void test() throws IOException, InterruptedException {
        LinuxWord2PDF.wordToPdf("D:\\文档资料\\三部软件研发-java.docx", "D:a.pdf");
    }
}
