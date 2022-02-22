package com.iscas.base.biz.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  替换请求体过滤器，使得requestbody可以被多个获取使用
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/2/21 15:52
 * @since jdk1.8
 */
public class ReplaceRequestBodyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest requestWrapper = new RequestWrapper(request);
        chain.doFilter(requestWrapper, response);
    }
}
