package com.iscas.common.tools.core.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256加密工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/3/14 13:22
 * @since jdk1.8
 */
@SuppressWarnings("unused")
public class Sha256Utils {
    private Sha256Utils() {
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 待加密的报文
     * @return String
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public static String getSHA256Str(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        String encodeStr;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
        encodeStr = byte2Hex(messageDigest.digest());
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes bytes
     * @return String
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
