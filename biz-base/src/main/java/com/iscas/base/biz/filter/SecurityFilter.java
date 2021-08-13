package com.iscas.base.biz.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安装相关过滤器
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/7 18:26
 * @since jdk1.8
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //设置X-Content-Type-Options，如果通过 script 参考检索到的响应中接收到 "nosniff" 指令，则浏览器不会加载“script”文件，
        // 除非 MIME 类型匹配以下值之一：application/ecmascript、application/javascript、application/x-javascript
        //text/ecmascript、text/javascript、text/jscript、text/x-javascript、text/vbs、text/vbscript"
        response.setHeader("X-Content-Type-Options", "nosniff");
        //设置X-XSS-Protection
        //0：禁用XSS保护；
        //1：启用XSS保护；
        //1; mode=block：启用XSS保护，并在检查到XSS攻击时，停止渲染页面（例如IE8中，检查到攻击时，整个页面会被一个#替换）；
        response.setHeader("X-XSS-Protection", "1; mode=block");

        //X-Frame-Options HTTP 响应头是微软提出来的一个HTTP响应头，主要用来给浏览器指示允许一个页面可否在 <frame>, <iframe> 或者
        //<object> 中展现的标记。网站可以使用此功能，来确保自己网站的内容没有被嵌到别人的网站中去，也从而避免了点击劫持 (ClickJacking{注1}) 的攻击。
        //# DENY
        //# 表示该页面不允许在frame中展示,即使在相同域名的页面中嵌套也不允许
        //# SAMEORIGIN
        //# 表示该页面可以在相同域名页面的frame中展示
        //# ALLOW-FROM url
        //# 表示该页面可以在指定来源的frame中展示
        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        filterChain.doFilter(request, response);
    }
}
