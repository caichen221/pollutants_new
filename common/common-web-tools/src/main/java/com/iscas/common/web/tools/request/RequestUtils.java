package com.iscas.common.web.tools.request;

import javax.servlet.http.HttpServletRequest;

/**
 * servlet请求的工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/16 15:58
 * @since jdk1.8
 */
public class RequestUtils {
    private RequestUtils() {}

    /**
     * 获取远程访问的IP，支持使用代理的服务获取原始的IP
     * */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            //如果没有代理服务，直接获取
            return request.getRemoteAddr();
        }
        //如果有代理服务，从header的x-forwarded-for中获取
        return request.getHeader("x-forwarded-for");
    }
}
