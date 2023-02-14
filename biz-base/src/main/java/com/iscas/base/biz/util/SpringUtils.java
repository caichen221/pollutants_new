package com.iscas.base.biz.util;

import com.iscas.common.tools.assertion.AssertObjUtils;
import com.iscas.common.tools.constant.CommonConstant;
import com.iscas.common.tools.constant.HeaderKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * spring相关操作工具类
 *
 * @author zhuquanwen
 **/
@SuppressWarnings({"unused", "unchecked"})
@Component
public class SpringUtils implements ApplicationContextAware, CommonConstant, HeaderKey {
    private static ApplicationContext applicationContext;

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 获取request header
     */
    public static String getReqHeader(String headerName) {
        HttpServletRequest request = getRequest();
        return request.getHeader(headerName);
    }

    /**
     * 获取response header
     */
    public static String getResHeader(String headerName) {
        HttpServletResponse response = getResponse();
        return response.getHeader(headerName);
    }

    /**
     * 设置response header
     */
    public static void setResHeader(String headerName, String headerVal) {
        HttpServletResponse response = getResponse();
        response.setHeader(headerName, headerVal);
    }

    /**
     * 设置request attribute
     */
    public static void setReqAttr(String key, Object val) {
        HttpServletRequest request = getRequest();
        request.setAttribute(key, val);
    }

    /**
     * 获取request attribute
     */
    public static <T> T getReqAttr(String key, Class<T> val) {
        HttpServletRequest request = getRequest();
        return (T) request.getAttribute(key);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attrs -> ((ServletRequestAttributes) attrs).getRequest())
                .orElse(null);
    }

    public static void main(String[] args) {
        HttpServletRequest request = getRequest();
        System.out.println(request);
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attrs -> ((ServletRequestAttributes) attrs).getResponse())
                .orElse(null);
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return Optional.ofNullable(getRequest()).map(HttpServletRequest::getSession).orElse(null);
    }

    /**
     * 获取session
     */
    public static HttpSession getSession(boolean flag) {
        return Optional.ofNullable(getRequest()).map(req -> req.getSession(flag)).orElse(null);
    }

    /**
     * 注册或替换一个Bean
     *
     * @param beanName beanName
     * @param bean     bean实例
     * @date 2023/2/14
     */
    public static void registerOrReplaceSingletonBean(String beanName, Object bean) {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) autowireCapableBeanFactory;
        if (defaultListableBeanFactory.containsBean(beanName)) {
            defaultListableBeanFactory.destroySingleton(beanName);
        }
        defaultListableBeanFactory.registerSingleton(beanName, bean);
    }

    public static String getIpAddr() {
        return getIpAddr(getRequest());
    }

    /**
     * 获取客户端地址
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String getIpAddr(HttpServletRequest request) {
        AssertObjUtils.assertNotNull(request, "未获取到request请求，无法获取客户端请求");
        String ipAddress;
        try {
            ipAddress = checkAndGetIpAddr(request, null, X_FORWARDED_FOR);
            ipAddress = checkAndGetIpAddr(request, ipAddress, PROXY_CLIENT_IP);
            ipAddress = checkAndGetIpAddr(request, ipAddress, WL_PROXY_CLIENT_IP);
            if (checkWrongIpAddr(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (Objects.equals(LOCAL_IP, ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        System.err.println("获取本机ip地址出错");
                    }
                }
            }
            // "***.***.***.***".length()
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ipAddress = "";
        }
        return ipAddress;
    }

    private static String checkAndGetIpAddr(HttpServletRequest request, String ipAddress, String elseHeaderKey) {
        if (checkWrongIpAddr(ipAddress)) {
            return request.getHeader(elseHeaderKey);
        }
        return ipAddress;
    }

    private static boolean checkWrongIpAddr(String ipAddress) {
        return ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtils.applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取springmvc 的URI对应的method
     * */
    public static Map<RequestMappingInfo, HandlerMethod> getMvcUriMethods() {
        ObjectProvider<RequestMappingHandlerMapping> beanProvider = applicationContext.getBeanProvider(RequestMappingHandlerMapping.class);
        return beanProvider.stream().filter(bp -> bp.getClass() == RequestMappingHandlerMapping.class).findFirst()
                .map(AbstractHandlerMethodMapping::getHandlerMethods).orElse(null);
    }

}
