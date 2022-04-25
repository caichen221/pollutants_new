package com.iscas.biz.controller.common;

import com.iscas.biz.service.common.VerificationCodeService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/8/17 20:49
 * @since jdk1.8
 */
@SuppressWarnings({"unused", "rawtypes"})
@RestController
@Api(tags = "验证码控制器")
@Slf4j
@ConditionalOnProperty(havingValue = "true", value = "kaptcha.enabled")
@Validated
@RequestMapping("/verification/code")
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
    @GetMapping
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
    @GetMapping("/verify")
    public ResponseEntity verify(@NotBlank(message = "验证码不能为空") String code, @NotBlank(message = "加密码不能为空") String key) throws Exception {
        return verificationCodeService.verify(code, key);
    }

}
