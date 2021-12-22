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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;
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


//    @GetMapping("/xcode")
//    public Result getXCode1(HttpServletResponse resp, HttpServletRequest request) throws IOException {
//
//        // 随机选择背景图
//        int num = new Random().nextInt(10) + 1;
//        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("xcode/" + num + ".jpg");
//        BufferedImage src= ImageIO.read(resourceAsStream);
//        //移动图
//        BufferedImage newSrc=new BufferedImage(src.getWidth(), src.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);//新建一个类型支持透明的BufferedImage
//        //对比图
//        BufferedImage newSrc2=new BufferedImage(src.getWidth(), src.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);//新建一个类型支持透明的BufferedImage
//
//        //抠块的大小
//        int blockWidth=48;
//        int blockHeight=48;
//
//        // 用于生成 移动图
//        XCodeTdo code = new XCodeTdo();
//        code.setNum(num);
//
//        Random rand1=new Random();
//        int x=rand1.nextInt(src.getWidth()-blockWidth-20)+20;//10,width-200
//        if (x > 210 - 58) {
//            x = 210 - 58;
//        }
//
//        Random rand2=new Random();
//        int y=rand2.nextInt(src.getHeight()-blockHeight-20)+20;//
//
//        code.setX(x);
//        code.setY(y);
//
//        int ca = new Random().nextInt(blockWidth-2*20)+(x+20);
//        int cb = new Random().nextInt(blockHeight-2*20)+(y+20);
//        code.setCa(ca);
//        code.setCb(cb);
//
//        tt.cutByTemplate2(src,newSrc,newSrc2,x,y,blockWidth,blockHeight, ca, cb);//图片大小是固定，位置是随机
//
//        request.getSession().setAttribute("X_CODE", JSON.toJSONString(code));
//
//        //生成对比图
//        ImageIO.write(newSrc2, "png", resp.getOutputStream());
//        return null;
//    }
}
