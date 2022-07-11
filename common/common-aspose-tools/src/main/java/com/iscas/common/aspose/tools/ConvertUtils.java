package com.iscas.common.aspose.tools;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.SaveFormat;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 转换工具类，word转PDF等
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 11:30
 * @since jdk11
 */
@SuppressWarnings("unused")
public class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * HTML转为WORD,docx格式
     *
     * @param htmlText html文本
     * @param os       输出流 转换后的word，docx格式
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void html2docx(String htmlText, OutputStream os) throws Exception {
        html2Word(htmlText, os, SaveFormat.DOCX);
    }

    /**
     * HTML转为WORD,doc格式
     *
     * @param htmlText html文本
     * @param os       输出流 转换后的word,doc格式
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void html2doc(String htmlText, OutputStream os) throws Exception {
        html2Word(htmlText, os, SaveFormat.DOC);
    }

    /**
     * HTML转为WORD
     *
     * @param htmlText   html文本
     * @param os         输出流 转换后的word
     * @param saveFormat 输出的格式。{@link SaveFormat#DOC} 或 {@link SaveFormat#DOCX}
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void html2Word(String htmlText, OutputStream os, int saveFormat) throws Exception {
        LicenseUtils.initLicense();
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHtml(htmlText);
        doc.save(os, saveFormat);
    }

    /**
     * word转为HTML
     *
     * @param is 输入流 待转换的word
     * @param os 输出流 转换后的html
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void word2html(InputStream is, OutputStream os) throws Exception {
        LicenseUtils.initLicense();
        Document doc = new Document(is);
        HtmlSaveOptions opts = new HtmlSaveOptions(SaveFormat.HTML);
        opts.setExportXhtmlTransitional(true);
        opts.setExportImagesAsBase64(true);
        opts.setExportPageSetup(true);
        doc.save(os, opts);
    }

    /**
     * word转为PDF
     *
     * @param is 输入流
     * @param os 输出流
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void word2pdf(InputStream is, OutputStream os) throws Exception {
        convert(is, os, SaveFormat.PDF);
    }

    /**
     * 格式转换
     *
     * @param is         输入流
     * @param os         输出流
     * @param saveFormat 文件格式,参见{@link SaveFormat}
     * @throws Exception 异常
     * @date 2022/7/9
     * @since jdk11
     */
    public static void convert(InputStream is, OutputStream os, int saveFormat) throws Exception {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        LicenseUtils.initLicense();
        long old = System.currentTimeMillis();
        Document doc = new Document(is);
        // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
        doc.save(os, saveFormat);
        long now = System.currentTimeMillis();
        System.out.println("Word转换成功，共耗时：" + (now - old) + "毫秒");
    }

}
