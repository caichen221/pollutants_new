package com.iscas.common.web.tools.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/31 11:06
 * @since jdk1.8
 */
public class IOUtils {
    private IOUtils() {}

    public static void transferTo(InputStream is, OutputStream os) throws IOException {
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff)) > 0) {
            os.write(buff, 0, len);
        }
    }
}
