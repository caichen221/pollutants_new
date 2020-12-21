package com.iscas.common.tools.captcha;

import lombok.Cleanup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/21 9:29
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class CaptchaTests {
    @Test
    public void test() throws IOException {
        File file = new File("d:/captcha.jpg");
        @Cleanup OutputStream os = new FileOutputStream(file);
        String captcha = CaptchaUtils.createCaptcha(os);
        System.out.println(captcha);
        Assert.assertNotNull(captcha);
    }
}
