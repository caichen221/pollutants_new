package com.iscas.base.biz.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.service.common.SpringService;
import com.iscas.common.tools.core.date.DateRaiseUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthenticationRuntimeException;
import com.iscas.templet.exception.ValidTokenException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * JWT工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 22:29
 * @since jdk1.8
 */
public class JWTUtils {
    private JWTUtils(){}
    public static final String SECRET = "ISCAS";
    public static String createToken(String username, int expire) throws UnsupportedEncodingException {
        Date iatDate = new Date();
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.MINUTE,expire);
//        Date expiresDate = nowTime.getTime();
        Date expiresDate = DateRaiseUtils.afterOffsetDate(expire * 60 * 1000L);
        Map<String, Object> map = new HashMap<>(2 << 2);
        map.put("alg", "HS256");
        map.put("typ","JWT");
        String token = JWT.create()
                .withHeader(map)
                .withClaim("username", username)
                .withClaim("date", iatDate)
                .withExpiresAt(expiresDate)
                .withIssuedAt(iatDate)
                .sign(Algorithm.HMAC256(SECRET));

        //将token缓存起来
//        CaffCacheUtils.set(token, iatDate);
        IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
        authCacheService.set(token, iatDate);
        return token;
    }
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException, ValidTokenException {
//        Object obj = CaffCacheUtils.get(token);
        IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
        Object obj = authCacheService.get(token);
        if(obj == null){
            throw new ValidTokenException("登录凭证校验失败","token:" + token + "不存在或已经被注销");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = jwtVerifier.verify(token);
        }catch (Exception e){
            throw new ValidTokenException("登录凭证校验失败","token:" + token + "校验失败");
        }
        return decodedJWT.getClaims();
    }

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
            throw new AuthenticationRuntimeException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
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
            throw new AuthenticationRuntimeException("未获取到当前登录的用户信息");
        } catch (UnsupportedEncodingException e) {
            throw new AuthenticationRuntimeException("未获取到当前登录的用户信息");
        }
        IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
//        String tokenx = (String) CaffCacheUtils.get("user-token" + username);
        String tokenx = (String) authCacheService.get("user-token" + username);
        if(!Objects.equals(token, tokenx)){
            throw new AuthenticationRuntimeException("身份认证信息有误", "token有误或已被注销");
        }
        return username;
    }

}
