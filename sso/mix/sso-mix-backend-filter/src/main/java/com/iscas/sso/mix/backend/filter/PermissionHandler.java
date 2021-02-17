package com.iscas.sso.mix.backend.filter;

import com.iscas.templet.exception.AuthorizationException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 9:49
 * @since jdk1.8
 */
public interface PermissionHandler {
    void handler(String user) throws AuthorizationException;
}
