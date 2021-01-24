package com.iscas.springboot.samples.filter.filter4;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 20:45
 * @since jdk1.8
 */
@Component("filter4")
public class Filter4 implements Filter {
    @Autowired
    private  TestService testService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========filter4===========");
        testService.test();
        chain.doFilter(request, response);
    }
}
