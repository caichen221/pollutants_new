package com.iscas.springboot.samples.filter.filter2;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 19:36
 * @since jdk1.8
 */
@Component
@Order(-2)
public class Filter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========filter2===========");
        chain.doFilter(request, response);
    }
}
