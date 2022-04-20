package com.iscas.base.biz.util;

import com.iscas.base.biz.model.auth.AuthContext;
import com.iscas.templet.exception.AuthenticationRuntimeException;

import java.util.Optional;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/21 11:02
 * @since jdk1.8
 */
public class AuthContextHolder {
    private AuthContextHolder() {
    }

    private static ThreadLocal<AuthContext> context = new ThreadLocal<>();

    public static void setContext(AuthContext authContext) {
        context.set(authContext);
    }

    public static AuthContext getContext() {
        return context.get();
    }

    public static void removeContext() {
        context.remove();
    }


    public static Integer getUserId() {
        return Optional.ofNullable(AuthContextHolder.getContext())
                .map(context -> (Integer) context.getUserId())
                .orElseThrow(() -> new AuthenticationRuntimeException("用户未登录"));
    }

    public static String getUsername() {
        return Optional.ofNullable(AuthContextHolder.getContext())
                .map(context -> context.getUsername())
                .orElseThrow(() -> new AuthenticationRuntimeException("用户未登录"));
    }
}
