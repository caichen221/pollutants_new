package com.iscas.biz.security.handler;

import com.iscas.biz.security.util.OutputUtils;
import com.iscas.common.web.tools.json.JsonUtils;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 授权失败处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/03/14 15:09
 * @since jdk1.8
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage("没有权限");
        OutputUtils.output(httpServletResponse, 403, responseEntity);
    }
}
