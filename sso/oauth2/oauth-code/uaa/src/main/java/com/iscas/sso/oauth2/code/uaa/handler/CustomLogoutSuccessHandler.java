package com.iscas.sso.oauth2.code.uaa.handler;

import com.iscas.sso.oauth2.code.uaa.util.JWTUtils;
import com.iscas.sso.oauth2.code.uaa.util.OutputUtils;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登出处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/03/14 15:16
 * @since jdk1.8
 */

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ResponseEntity responseEntity = new ResponseEntity();
        String authorization = httpServletRequest.getHeader("Authorization");
        JWTUtils.TOKENS.remove(authorization);
        responseEntity.setMessage("登出成功");
        OutputUtils.output(httpServletResponse, 200, responseEntity);
    }
}

