package com.iscas.base.biz.util;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.base.biz.config.Constants;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthorizationRuntimeException;
import com.iscas.templet.exception.ValidTokenException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/2/21 9:07
 * @since jdk1.8
 */
public class UserUtils {
    private UserUtils() {}
    /**
     * 获取当前登录的用户名
     * */
    public static String getLoginUsername() {
        HttpServletRequest request = SpringUtils.getRequest();
        String token = null;
        token = request.getHeader(Constants.TOKEN_KEY);
        if (token == null) {
            //尝试从cookie中拿author
            Cookie cookie = CookieUtils.getCookieByName(request, Constants.TOKEN_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        if (token == null) {
            throw new AuthorizationRuntimeException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
        }

        //如果token不为null,校验token
        String username = null;
        try {
            Map<String, Claim> clainMap = JWTUtils.verifyToken(token);
            username = clainMap.get("username").asString();
            if (username == null) {
                throw new ValidTokenException("token 校验失败");
            }
        } catch (ValidTokenException e) {
            throw new AuthorizationRuntimeException("未获取到当前登录的用户信息");
        } catch (UnsupportedEncodingException e) {
            throw new AuthorizationRuntimeException("未获取到当前登录的用户信息");
        }
//        String tokenx = (String) CaffCacheUtils.get("user-token" + username);
//        if(!Objects.equals(token, tokenx)){
//            throw new AuthorizationRuntimeException("身份认证信息有误", "token有误或已被注销");
//        }
        return username;
    }
}
