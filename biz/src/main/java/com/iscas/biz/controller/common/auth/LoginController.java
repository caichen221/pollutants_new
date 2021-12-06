package com.iscas.biz.controller.common.auth;


import com.iscas.base.biz.aop.auth.SkipAuthentication;
import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.auth.TokenProps;
import com.iscas.base.biz.service.AbstractAuthService;
import com.iscas.base.biz.util.LoginCacheUtils;
import com.iscas.biz.validator.anno.LoginConstraint;
import com.iscas.common.tools.core.random.RandomStringUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登陆控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 22:38
 * @since jdk1.8
 */
@RestController
@Api(tags = "登陆控制器")
@SkipAuthentication
@Validated
public class LoginController extends BaseController implements Constants {


    private final TokenProps tokenProps;

    private final AbstractAuthService authService;

    public LoginController(TokenProps tokenProps, AbstractAuthService authService) {
        this.tokenProps = tokenProps;
        this.authService = authService;
    }

    @ApiOperation(value="[登录控制器]用户登出", notes="create by:朱全文 2020-02-21")
    @GetMapping(value = "/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity logout(HttpServletRequest request){
        ResponseEntity responseEntity = new ResponseEntity(200, "注销成功");
        authService.invalidToken(request);
        responseEntity.setValue("注销成功");
        return responseEntity;
    }

    @ApiOperation(value="[登录控制器]登录前置", notes="create by:朱全文 2020-02-21")
    @GetMapping(value = "/prelogin", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map> preLogin(){
        ResponseEntity<Map> responseEntity = new ResponseEntity<>();
        String data = RandomStringUtils.randomStr(16);
        String uuid = LoginCacheUtils.createCodeAndPut(data);
        responseEntity.setValue(Map.of("key", uuid, "encryKey", data));
        return responseEntity;
    }

    @ApiOperation(value="[登录控制器]登录", notes="create by:朱全文 2020-02-21")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "user", value = "用户名密码等信息", required = true, dataType = "Map")
            }
    )
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity login(HttpServletResponse response, @RequestBody @LoginConstraint Map<String,String> user) throws Exception {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage("登录成功");
        authService.loginHandler(response,  user,
                responseEntity, ((Long) tokenProps.getExpire().toMinutes()).intValue(), tokenProps.getCookieExpire());
        return responseEntity;
    }
}
