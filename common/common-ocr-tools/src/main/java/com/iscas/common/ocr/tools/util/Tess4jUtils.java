package com.iscas.common.ocr.tools.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/11/18 19:57
 * @since jdk11
 */
public class Tess4jUtils {
    private Tess4jUtils() {}

    /**
     * 从图片中提取文字,默认设置英文字库,使用classpath目录下的训练库
     * @param path
     * @return
     */
    public static String readChar(String path){
        return null;
//        // JNA Interface Mapping
//        ITesseract instance = new Tesseract();
//        // JNA Direct Mapping
//        // ITesseract instance = new Tesseract1();
//        File imageFile = new File(path);
//        //In case you don't have your own tessdata, let it also be extracted for you
//        //这样就能使用classpath目录下的训练库了
//        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
//        //Set the tessdata path
//        instance.setDatapath(tessDataFolder.getAbsolutePath());
//        //英文库识别数字比较准确
//        instance.setLanguage(Const.ENG);
//        return getOCRText(instance, imageFile);
    }

//    /**
//     * 从图片中提取文字
//     * @param path 图片路径
//     * @param dataPath 训练库路径
//     * @param language 语言字库
//     * @return
//     */
//    public static String readChar(String path, String dataPath, String language){
//        File imageFile = new File(path);
//        ITesseract instance = new Tesseract();
//        instance.setDatapath(dataPath);
//        //英文库识别数字比较准确
//        instance.setLanguage(language);
//        return getOCRText(instance, imageFile);
//    }
//
//    /**
//     * 识别图片文件中的文字
//     * @param instance
//     * @param imageFile
//     * @return
//     */
//    private static String getOCRText(ITesseract instance, File imageFile){
//        String result = null;
//        try {
//            result = instance.doOCR(imageFile);
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    public static void main(String[] args) {
//        /*String path = "src/main/resources/image/text.png";
//        System.out.println(readChar(path));*/
//
//        String ch = "src/main/resources/image/ch.png";
//        System.out.println(readChar(ch, "src/main/resources", Const.CHI_SIM));
//    }

    public static void main(String[] args) throws IOException {
        // 创建实例
        ITesseract instance = new Tesseract();
        instance.setDatapath("D:\\ocr\\Tess4J\\tessdata");

        // 设置识别语言

        instance.setLanguage("chi_sim");

        // 设置识别引擎

//        instance.setOcrEngineMode(1);

        // 读取文件

        BufferedImage image = ImageIO.read(new File("d:/ocr/微信图片_20221118193524.jpg"));

        try {

            // 识别
            String result = instance.doOCR(image);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }


    }

}
