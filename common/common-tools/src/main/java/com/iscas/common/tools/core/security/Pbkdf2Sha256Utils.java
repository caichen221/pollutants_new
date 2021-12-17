package com.iscas.common.tools.core.security;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
 
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * pbkdf2_sha256加密验证算法
 *
 *
 */

public class Pbkdf2Sha256Utils {

    private static final Logger logger = LoggerFactory.getLogger(Pbkdf2Sha256Utils.class);
    //默认迭代计数为 20000
    private static final Integer DEFAULT_ITERATIONS = 20000;
    //算法名称
    private static final String algorithm = "pbkdf2_sha256";

    /**
     * 获取密文
     *
     * @param password   密码明文
     * @param salt       加盐
     * @param iterations 迭代计数
     * @return
     */
    private static String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm", e);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            logger.error("Could NOT generate secret key", e);
        }
        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);
        String str = new String(hashBase64);
        Arrays.fill(hashBase64, (byte) 0);
        return str;
    }

    /**
     * 密文加盐
     *
     * @return String
     */
    private static String getsalt() {
        int length = 12;
        Random rand = new Random();
        char[] rs = new char[length];
        for (int i = 0; i < length; i++) {
            int t = rand.nextInt(3);
            if (t == 0) {
                rs[i] = (char) (rand.nextInt(10) + 48);
            } else if (t == 1) {
                rs[i] = (char) (rand.nextInt(26) + 65);
            } else {
                rs[i] = (char) (rand.nextInt(26) + 97);
            }
        }
        return new String(rs);
    }

    /**
     * rand salt
     * iterations is default 20000
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        return encode(password, getsalt());
    }

    /**
     * rand salt
     *
     * @param password
     * @return
     */
    public static String encode(String password, int iterations) {
        return encode(password, getsalt(), iterations);
    }

    /**
     * iterations is default 20000
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encode(String password, String salt) {
        return encode(password, salt, DEFAULT_ITERATIONS);
    }

    /**
     * @param password   密码明文
     * @param salt       加盐
     * @param iterations 迭代计数
     * @return
     */
    public static String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    /**
     * 校验密码是否合法
     *
     * @param password       明文
     * @param hashedPassword 密文
     * @return
     */
    public static boolean verification(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = null;
        try {
            hash = encode(password, salt, iterations);
            return hash.equals(hashedPassword);
        } finally {
            hash = null;
        }
    }
}