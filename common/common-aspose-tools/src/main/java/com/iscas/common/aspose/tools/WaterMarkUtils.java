package com.iscas.common.aspose.tools;

import com.aspose.words.*;
import com.aspose.words.Shape;

import java.awt.*;
import java.io.InputStream;

/**
 * 添加水印的方法
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 12:58
 * @since jdk11
 */
public class WaterMarkUtils {
    private WaterMarkUtils() {}

    public static void addWatermarkText(InputStream is, String text) throws Exception {
        LicenseUtils.initLicense();
        Document doc = new Document(is);
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        watermark.setName("WaterMark");
        watermark.getTextPath().setText(text);
        watermark.getTextPath().setFontFamily("Arial");
        watermark.setWidth(500);
        watermark.setHeight(100);
        watermark.setRotation(-40);
        watermark.getFill().setColor(Color.GRAY);
        watermark.setStrokeColor(Color.GRAY);
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        watermark.setWrapType(WrapType.NONE);
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);
        for (Section sect : doc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null) {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        header.appendChild(watermarkPara.deepClone(true));
    }

}
