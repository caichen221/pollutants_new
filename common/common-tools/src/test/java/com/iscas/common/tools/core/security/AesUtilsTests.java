package com.iscas.common.tools.core.security;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * AES加解密测试类
 * @author zhuquanwen
 **/
public class AesUtilsTests {

    /**
     * 测试AES 默认加密
     * */
    @Test
    public void aesEncrpty() throws Exception {
        String sec = AesUtils.aesEncrypt("gfdx");
        Assertions.assertEquals("3Dk6JhPSftrUBxxWRYfA1w==", sec);
    }

    /**
     *
     * 测试AES默认解密
     * */
    @Test
    public void aesDecrpty() throws Exception {
        String ori = AesUtils.aesDecrypt("VftCp3FzK7UeUThCOI+3DQ==");
        Assertions.assertEquals("admin", ori);
    }

    /**
     * 测试AES 带KEY加密
    * */
    @Test
    public void aesEncrptyWithKey() throws Exception {
        String k = "6x9o67h5BO205Cfv";
        String sec = AesUtils.aesEncrypt("admin", k);
        Assertions.assertEquals("q2F6LtquPxijSre3os07Dg==", sec);
    }

    /**
     * 测试AES 带KEY解密
     * */
    @Test
    public void aesDecrptyWithKey() throws Exception {
        String k = "6x9o67h5BO205Cfv";
        String ori = AesUtils.aesDecrypt("q2F6LtquPxijSre3os07Dg==", k);
        Assertions.assertEquals("admin", ori);
    }

}
