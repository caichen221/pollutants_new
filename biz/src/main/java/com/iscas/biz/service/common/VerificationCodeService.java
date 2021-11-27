package com.iscas.biz.service.common;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.iscas.base.biz.util.LoginCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.tools.constant.HeaderKey;
import com.iscas.common.tools.constant.MediaType;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/27 20:11
 * @since jdk1.8
 */
@Service
@Slf4j
public class VerificationCodeService implements HeaderKey, MediaType {
    @Autowired
    private Producer producer;

    public void verificationCode(String key) throws LoginException, IOException {
        HttpServletResponse response = SpringUtils.getResponse();
        String loginKey = LoginCacheUtils.get(key);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝生成验证码");
        }
        response.setDateHeader(EXPIRES, 0);
        response.setHeader(CACHE_CONTROL, "no-store, no-cache, must-revalidate");
        response.addHeader(CACHE_CONTROL, "post-check=0, pre-check=0");
        response.setHeader(PRAGMA, "no-cache");
        response.setContentType(IMAGE_JPEG);
        String capText = producer.createText();

        log.debug("*************验证码已经生成为：{}******************", capText);

        LoginCacheUtils.put(Constants.KAPTCHA_SESSION_KEY + ":" + loginKey, capText);
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // 向页面输出验证码
        ImageIO.write(bi, "jpg", out);
        out.flush();
    }

    public ResponseEntity verify(String code, String key) throws LoginException {
        String loginKey = LoginCacheUtils.get(key);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝校验验证码");
        }
        String cacheKey = Constants.KAPTCHA_SESSION_KEY + ":" + loginKey;
        ResponseEntity response = new ResponseEntity();
        // 获取验证码
        Object storeCode = LoginCacheUtils.get(cacheKey);
        // 判断验证码是否为空
        if (StringUtils.isEmpty(code)) {
            response.setValue(false);
        } else {
            // 校验验证码的正确与否
            boolean result = StringUtils.equalsAnyIgnoreCase(code, storeCode + "");
            LoginCacheUtils.remove(cacheKey);
            if (!result) {
                LoginCacheUtils.remove(key);
            }
            response.setValue(result);
        }
        return response;
    }
}
