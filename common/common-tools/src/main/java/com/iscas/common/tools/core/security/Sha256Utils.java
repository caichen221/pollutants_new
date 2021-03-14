package com.iscas.common.tools.core.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256加密工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/14 13:22
 * @since jdk1.8
 */
public class Sha256Utils {
    private Sha256Utils() {
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 待加密的报文
     * @return
     */
    public static String getSHA256Str(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest;
        String encodeStr = "";
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
        encodeStr = byte2Hex(messageDigest.digest());
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

//    /**
//     * 获取随机盐
//     * @return
//     */
//    public static String getSalt() throws NoSuchProviderException, NoSuchAlgorithmException {
//        SecureRandom sr;
//        byte[] salt = new byte[16];
//        sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
//        sr.nextBytes(salt);
//        return salt.toString();
//    }

//    /**
//     * sha2 加盐加密
//     * @param encryptStr 需要加密的字符串
//     * @param salt 盐
//     * @return
//     */
//    public static String getSHA256StrWithSalt(String encryptStr, String salt) throws NoSuchAlgorithmException {
//        MessageDigest md = null;
//        String encryptCode = null;
//        byte[] bt = (encryptStr + salt).getBytes();
//        md = MessageDigest.getInstance("SHA-256");
//        md.update(bt);
//        encryptCode = byte2Hex(md.digest());
//        return encryptCode;
//    }

}
