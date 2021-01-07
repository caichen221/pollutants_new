package com.iscas.common.tools.core.io.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/7/12 14:56
 * @since jdk1.8
 */
public class GzipUtils {
    private GzipUtils() {}

    /**
     * 字符串压缩
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param str 字符串
     * @throws
     * @return byte[]
     */
    public static byte[] compress(String str) throws IOException {
        if (str == null) {
            return null;
        }
        return compressFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解压
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param bytes 字节数组
     * @throws
     * @return java.lang.String
     */
    public static String uncompress(byte[] bytes) throws IOException {
        byte[] bytes1 = uncompressToBytes(bytes);
        return new String(bytes1, StandardCharsets.UTF_8);
    }

    /**
     * 字节数组压缩
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param bytes 字节数组
     * @throws
     * @return byte[]
     */
    public static byte[] compressFromBytes(byte[] bytes) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(bytes);
            }
            return out.toByteArray();
            //return out.toString(StandardCharsets.ISO_8859_1);
            // Some single byte encoding
        }
    }

    /**
     * 字节数组解压
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param bytes 字节数组
     * @throws
     * @return byte[]
     */
    public static byte[] uncompressToBytes(byte[] bytes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            int b;
            while ((b = gis.read()) != -1) {
                baos.write((byte) b);
            }
        }
        return baos.toByteArray();
    }
}
