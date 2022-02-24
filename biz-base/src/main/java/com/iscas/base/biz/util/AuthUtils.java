package com.iscas.base.biz.util;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.autoconfigure.auth.TokenProps;
import com.iscas.common.tools.constant.CommonConstant;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthenticationRuntimeException;
import com.iscas.templet.exception.ValidTokenException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Optional;

/**
 * 用户工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/2/21 9:07
 * @since jdk1.8
 */
public class AuthUtils {

    private AuthUtils() {
    }

    /**
     * 获取当前登录的用户名
     */
    public static String getLoginUsername() {
        String token = getToken();
        if (token == null) {
            throw new AuthenticationRuntimeException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
        }

        //如果token不为null,校验token
        String username = null;
        try {
            Map<String, Claim> clainMap = JWTUtils.verifyToken(token, SpringUtils.getBean(TokenProps.class).getCreatorMode());
            username = clainMap.get("username").asString();
            if (username == null) {
                throw new ValidTokenException("token 校验失败");
            }
        } catch (ValidTokenException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthenticationRuntimeException("未获取到当前登录的用户信息");
        }
//        String tokenx = (String) CaffCacheUtils.get("user-token:" + username);
//        if(!Objects.equals(token, tokenx)){
//            throw new AuthorizationRuntimeException("身份认证信息有误", "token有误或已被注销");
//        }
        return username;
    }

    /**
     * 获取token
     * 先从header中获取，如果没有从cookie中获取
     */
    public static String getToken() {
        return getToken(SpringUtils.getRequest());
    }

    /**
     * 获取token
     * 先从header中获取，如果没有从cookie中获取
     */
    public static String getToken(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getHeader(Constants.TOKEN_KEY))
                .orElse(Optional.ofNullable(CookieUtils.getCookieByName(request, Constants.TOKEN_KEY))
                        .map(Cookie::getValue)
                        .orElse(null));
        //如果有bearer开头，去掉
        return Optional.ofNullable(token)
                .filter(t -> t.startsWith(CommonConstant.BEARER))
                .map(t -> StringUtils.substringAfter(t, CommonConstant.BEARER).trim())
                .orElse(token);
    }

}
