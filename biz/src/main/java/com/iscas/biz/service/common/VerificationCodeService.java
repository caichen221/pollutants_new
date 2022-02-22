package com.iscas.biz.service.common;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.service.common.AuthCacheService;
import com.iscas.base.biz.util.AuthCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.tools.constant.HeaderKey;
import com.iscas.common.tools.constant.MediaType;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.driver.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    @Autowired
    private IAuthCacheService authCacheService;

    public void verificationCode(String key) throws LoginException, IOException {
        HttpServletResponse response = SpringUtils.getResponse();
        String loginKey = (String) authCacheService.get(key, com.iscas.base.biz.config.Constants.LOGIN_CACHE);
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

        Environment environment = SpringUtils.getApplicationContext().getBean(Environment.class);
        String loginRandomCacheTime = environment.getProperty("login.random.data.cache.time-to-live");
        int loginRandomCacheSenconds = Integer.parseInt(loginRandomCacheTime);
        authCacheService.set(Constants.KAPTCHA_SESSION_KEY + ":" + loginKey, capText, com.iscas.base.biz.config.Constants.LOGIN_CACHE, loginRandomCacheSenconds);
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // 向页面输出验证码
        ImageIO.write(bi, "jpg", out);
        out.flush();
    }

    public ResponseEntity verify(String code, String key) throws LoginException {
        String loginKey = (String) authCacheService.get(key, com.iscas.base.biz.config.Constants.LOGIN_CACHE);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝校验验证码");
        }
        String cacheKey = Constants.KAPTCHA_SESSION_KEY + ":" + loginKey;
        ResponseEntity response = new ResponseEntity();
        // 获取验证码
        Object storeCode = authCacheService.get(cacheKey, com.iscas.base.biz.config.Constants.LOGIN_CACHE);
        // 判断验证码是否为空
        if (StringUtils.isEmpty(code)) {
            response.setValue(false);
        } else {
            // 校验验证码的正确与否
            boolean result = StringUtils.equalsAnyIgnoreCase(code, storeCode + "");
            authCacheService.remove(cacheKey, com.iscas.base.biz.config.Constants.LOGIN_CACHE);
            if (!result) {
                authCacheService.remove(key, com.iscas.base.biz.config.Constants.LOGIN_CACHE);
            }
            response.setValue(result);
        }
        return response;
    }
}
