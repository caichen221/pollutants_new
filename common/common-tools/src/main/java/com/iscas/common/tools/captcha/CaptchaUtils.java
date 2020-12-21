package com.iscas.common.tools.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/21 9:20
 * @since jdk1.8
 */
public class CaptchaUtils {
    private CaptchaUtils() {}

    private static String RANDOM_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";  //随机产生数字与字母组合的字符串
    // 设置图片属性
    private static int DEFAULT_WIDTH = 95;// 图片宽
    private static int DEFAULT_HEIGHT = 25;// 图片高
    private static int LINE_SIZE = 40;// 干扰线数量
    private static int RADNOM_STR_NUM = 4;// 随机产生字符数量


    /**
     * 生成验证码图片，使用默认值
     * 返回验证的字符串
     * */
    public static String createCaptcha(OutputStream os) throws IOException {
        return createCaptcha(DEFAULT_WIDTH, DEFAULT_HEIGHT, LINE_SIZE, RADNOM_STR_NUM, os);
    }


    /**
     * 生成验证码图片
     * 返回验证的字符串
     * */
    public static String createCaptcha(int width, int height, int lineSize, int randomStrNum, OutputStream os) throws IOException {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);//图片大小
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小
        g.setColor(getRandColor(110, 133));//字体颜色
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g, width, height);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= randomStrNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        g.dispose();

        // 将内存中的图片通过流动形式输出到客户端
        ImageIO.write(image, "JPEG", os);
        return randomString;
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 绘制干扰线
     */
    private static void drowLine(Graphics g, int width, int height) {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }


    /**
     * 绘制字符串
     */
    private static String drowString(Graphics g, String randomString, int i) {
        Random random = new Random();
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(RANDOM_STRING
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }


    /**
     * 获取随机的字符
     */
    public static String getRandomString(int num) {
        return String.valueOf(RANDOM_STRING.charAt(num));
    }


    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }
}
