package com.iscas.springboot.samples.filter.filter3;


import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 19:36
 * @since jdk1.8
 */
public class Filter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========filter3===========");
        chain.doFilter(request, response);
    }
}
