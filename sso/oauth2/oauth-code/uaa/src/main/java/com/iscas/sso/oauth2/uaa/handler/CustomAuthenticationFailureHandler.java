package com.iscas.sso.oauth2.uaa.handler;

import com.iscas.sso.oauth2.uaa.util.OutputUtils;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆失败处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/03/14 14:32
 * @since jdk1.8
 */

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage("登录失败");
        OutputUtils.output(httpServletResponse, 401, responseEntity);
    }
}
