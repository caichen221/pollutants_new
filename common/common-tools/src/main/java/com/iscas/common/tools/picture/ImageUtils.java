package com.iscas.common.tools.picture;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/24 21:10
 * @since jdk1.8
 */
public class ImageUtils {
    private ImageUtils() {
    }

    /**
     * 将base64转为图片
     * */
    public static void convertBase64ToImage(String base64, OutputStream os) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        // 解密
        byte[] b = decoder.decode(base64);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        os.write(b);
        os.flush();
    }
}
