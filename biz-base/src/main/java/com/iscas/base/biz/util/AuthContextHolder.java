package com.iscas.base.biz.util;

import com.iscas.base.biz.model.auth.AuthContext;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/21 11:02
 * @since jdk1.8
 */
public class AuthContextHolder {
    private AuthContextHolder() {}
    private static ThreadLocal<AuthContext> context = new ThreadLocal<>();
    public static void setContext(AuthContext authContext) {
        context.set(authContext);
    }
    public static AuthContext getContext() {
        return context.get();
    }
}
