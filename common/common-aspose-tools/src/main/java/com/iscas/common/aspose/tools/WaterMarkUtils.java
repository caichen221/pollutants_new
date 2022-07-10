package com.iscas.common.aspose.tools;

import com.aspose.words.*;
import com.aspose.words.Shape;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 添加水印的方法
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 12:58
 * @since jdk11
 */
public class WaterMarkUtils {
    private WaterMarkUtils() {}

    public static void addWatermarkText(InputStream is, String text, OutputStream os) throws Exception {
        LicenseUtils.initLicense();
        Document doc = new Document(is);
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //水印内容
        watermark.getTextPath().setText(text);
        //水印字体
        watermark.getTextPath().setFontFamily("宋体");
        //水印宽度
        watermark.setWidth(100);
        //水印高度
        watermark.setHeight(20);
        //旋转水印
        watermark.setRotation(-40);
        //水印颜色 浅灰色
        watermark.getFill().setColor(Color.lightGray);
        watermark.setStrokeColor(Color.lightGray);
        //设置相对水平位置
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        //设置相对垂直位置
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        //设置包装类型
        watermark.setWrapType(WrapType.NONE);
        //设置垂直对齐
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置文本水平对齐方式
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);
        for (Section sect : doc.getSections())
        {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
        doc.save(os, SaveFormat.DOC);
    }

    /**
     * 在页眉中插入水印
     * @param watermarkPara
     * @param sect
     * @param headerType
     * @throws Exception
     */
    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception{
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null)
        {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        header.appendChild(watermarkPara.deepClone(true));
    }


    /**
     * 设置水印属性
     * @param doc
     * @param wmText
     * @param left
     * @param top
     * @return
     * @throws Exception
     */
    public static Shape ShapeMore(Document doc, String wmText, double left, double top)throws Exception{
        Shape waterShape = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //水印内容
        waterShape.getTextPath().setText(wmText);
        //水印字体
        waterShape.getTextPath().setFontFamily("宋体");
        //水印宽度
        waterShape.setWidth(40);
        //水印高度
        waterShape.setHeight(13);
        //旋转水印
        waterShape.setRotation(-40);
        //水印颜色 浅灰色
        /*waterShape.getFill().setColor(Color.lightGray);
        waterShape.setStrokeColor(Color.lightGray);*/
        waterShape.setStrokeColor(new Color(210,210,210));
        //将水印放置在页面中心
        waterShape.setLeft(left);
        waterShape.setTop(top);
        //设置包装类型
        waterShape.setWrapType(WrapType.NONE);
        return waterShape;
    }

    /**
     * 插入多个水印
     * @param mdoc
     * @param wmText
     * @throws Exception
     */
    public static void WaterMarkMore(Document mdoc, String wmText)throws Exception{
        Paragraph watermarkPara = new Paragraph(mdoc);
        for (int j = 0; j < 500; j = j + 100)
        {
            for (int i = 0; i < 700; i = i + 85)
            {
                Shape waterShape = ShapeMore(mdoc, wmText, j, i);
                watermarkPara.appendChild(waterShape);
            }
        }
        for (Section sect : mdoc.getSections())
        {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }



}
