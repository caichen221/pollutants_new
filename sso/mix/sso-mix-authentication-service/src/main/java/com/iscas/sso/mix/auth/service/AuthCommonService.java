package com.iscas.sso.mix.auth.service;

import com.iscas.sso.mix.auth.exception.LoginUserPwdErrorException;
import com.iscas.sso.mix.auth.util.ConfigUtils;
import com.iscas.sso.mix.auth.util.JWTUtils;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 12:45
 * @since jdk1.8
 */
public class AuthCommonService {

    public String login(String username, String password) throws UnsupportedEncodingException, LoginUserPwdErrorException {
        //todo 校验用户名和密码
        if (1 != 1) {
            throw new LoginUserPwdErrorException();
        }
        String token = JWTUtils.createToken(username, Integer.parseInt(ConfigUtils.readProp("auth.expire")));
        return token;
    }

}
