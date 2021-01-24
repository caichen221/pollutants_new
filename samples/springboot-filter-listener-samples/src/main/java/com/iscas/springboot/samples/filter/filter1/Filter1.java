package com.iscas.springboot.samples.filter.filter1;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 19:34
 * @since jdk1.8
 */
@WebFilter(urlPatterns = "/*")
public class Filter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========filter1===========");
        chain.doFilter(request, response);
    }
}
