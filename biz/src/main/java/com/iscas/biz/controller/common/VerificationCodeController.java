package com.iscas.biz.controller.common;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.iscas.base.biz.service.common.SpringService;
import com.iscas.base.biz.util.LoginCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.LoginException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/8/17 20:49
 * @since jdk1.8
 */
@RestController
@Api(tags = "验证码控制器")
@Slf4j
@ConditionalOnProperty(havingValue = "true", value = "kaptcha.enabled", matchIfMissing = false)
public class VerificationCodeController extends BaseController {
    private static Cache<String,String> fifoCache = CacheUtil.newFIFOCache(1000);
    @Autowired
    private Producer producer;

    /**
     * 获取验证码
     * */
    @ApiOperation(value="[验证码]获取验证码", notes="create by:朱全文 2020-02-21")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "key", value = "加密码", required = true, dataType = "String")
            }
    )
    @GetMapping("/verification/code")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
        String loginKey = LoginCacheUtils.get(key);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝生成验证码");
        }
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = producer.createText();


        log.debug("*************验证码已经生成为：{}******************", capText);
        // 将验证码存于session中,分布式部署需要共享session，或存入共享存储中
//        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //存入内存
        Environment environment = SpringService.getApplicationContext().getBean(Environment.class);
        String loginRandomCacheTime = environment.getProperty("login.random.data.cache.time-to-live");
        fifoCache.put(Constants.KAPTCHA_SESSION_KEY + ":" + loginKey, capText, TimeUnit.SECONDS.toMillis(Long.parseLong(loginRandomCacheTime)));
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // 向页面输出验证码
        ImageIO.write(bi, "jpg", out);
        try {
            // 清空缓存区
            out.flush();
        } finally {
            // 关闭输出流
//            out.close();
        }
    }

    /**
     * 校验验证码
     * */
    @ApiOperation(value="[验证码] 校验验证码", notes="create by:朱全文 2020-02-21")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "key", value = "加密码", required = true, dataType = "String")
            }
    )
    @GetMapping("/verification/code/verify")
    public ResponseEntity verify(String code, String key) throws Exception {
        String loginKey = LoginCacheUtils.get(key);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝校验验证码");
        }
        ResponseEntity response = getResponse();
        // 获取Session中验证码
//        Object storeCode = SpringUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Object storeCode = fifoCache.get(Constants.KAPTCHA_SESSION_KEY + ":" + loginKey);
        // 判断验证码是否为空
        if (StringUtils.isEmpty(code)) {
            response.setValue(false);
        } else {
            // 校验验证码的正确与否
            boolean result = StringUtils.equalsAnyIgnoreCase(code, storeCode + "");
//            if (result) {
//                //将验证码从session中删掉
//                SpringUtils.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
//            }
            fifoCache.remove(Constants.KAPTCHA_SESSION_KEY + ":" + loginKey);
            if (!result) {
                LoginCacheUtils.remove(key);
            }
            response.setValue(result);
        }
        return response;
    }
}
