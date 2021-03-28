package com.iscas.sso.oauth2.code.uaa.handler;

import com.iscas.sso.oauth2.code.uaa.util.CookieUtils;
import com.iscas.sso.oauth2.code.uaa.util.JWTUtils;
import com.iscas.sso.oauth2.code.uaa.util.OutputUtils;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆成功处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/03/14 14:33
 * @since jdk1.8
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage("登录成功");

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String jwtToken = JWTUtils.createToken(user.getUsername(), 5);
        responseEntity.setValue(jwtToken);

        //写入cookie
        CookieUtils.setCookie(httpServletResponse, "Authorization", jwtToken, -1);

        OutputUtils.output(httpServletResponse, 200, responseEntity);
    }
}