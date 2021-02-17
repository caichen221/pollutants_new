package com.iscas.sso.mix.ticket.share.agent.filter;


import org.springframework.util.Assert;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.DefaultCorsProcessor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 自定义跨域过滤器，可以通过springboot auto config 配置
 *
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2018/3/20 15:12
 * @Modified:
 **/
public class CustomCrosFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (CorsUtils.isCorsRequest(request)) {
            String origin = request.getHeader("Origin");
            if (origin == null || "null".equals(origin)) {
                origin = "*";
            }
            response.setHeader("Access-Control-Allow-Origin", origin);
            //服务器同意客户端发送cookies
            response.setHeader("Access-Control-Allow-Credentials", "true");

            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token,Authorization,Access-Control-Allow-Origin");
//            response.setHeader("Cache-Control", "no-cach");
            if (CorsUtils.isPreFlightRequest(request)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
