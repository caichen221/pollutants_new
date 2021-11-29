package com.iscas.biz.controller.common;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.iscas.base.biz.util.LoginCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.biz.service.common.VerificationCodeService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
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
@Validated
public class VerificationCodeController extends BaseController {
    @Autowired
    private VerificationCodeService verificationCodeService;

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
    public void getKaptchaImage(@NotBlank(message = "加密码不能为空") String key) throws Exception {
        verificationCodeService.verificationCode(key);
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
    public ResponseEntity verify(@NotBlank(message = "验证码不能为空") String code, @NotBlank(message = "加密码不能为空") String key) throws Exception {
        return verificationCodeService.verify(code, key);
    }
}
