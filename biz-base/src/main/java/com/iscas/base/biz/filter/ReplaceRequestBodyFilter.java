package com.iscas.base.biz.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  替换请求体过滤器，使得requestbody可以被多次获取使用
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/2/21 15:52
 * @since jdk1.8
 */
public class ReplaceRequestBodyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest requestWrapper = new RequestWrapper(request);
        chain.doFilter(requestWrapper, response);
    }
}
