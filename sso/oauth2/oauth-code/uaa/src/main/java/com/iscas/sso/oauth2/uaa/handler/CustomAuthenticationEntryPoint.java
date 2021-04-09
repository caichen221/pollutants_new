package com.iscas.sso.oauth2.uaa.handler;

import com.iscas.sso.oauth2.uaa.util.OutputUtils;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 未登陆
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/03/14 14:30
 * @since jdk1.8
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage("未登录");
        OutputUtils.output(httpServletResponse, 401, responseEntity);
    }
}
