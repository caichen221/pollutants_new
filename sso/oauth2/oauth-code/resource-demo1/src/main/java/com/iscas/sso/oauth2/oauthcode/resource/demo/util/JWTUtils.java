package com.iscas.sso.oauth2.oauthcode.resource.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iscas.templet.exception.ValidTokenException;

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
    public static Set<String> TOKENS = new HashSet<>();
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
        TOKENS.add(token);
        //将token缓存起来
//        CaffCacheUtils.set(token, iatDate);
//        IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
//        authCacheService.set(token, iatDate);
        return token;
    }
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException, ValidTokenException {
//        Object obj = CaffCacheUtils.get(token);
//        IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
//        Object obj = authCacheService.get(token);
//        if(obj == null){
//            throw new ValidTokenException("登录凭证校验失败","token:" + token + "不存在或已经被注销");
//        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = jwtVerifier.verify(token);
        }catch (Exception e){
            throw new ValidTokenException("登录凭证校验失败","token:" + token + "校验失败");
        }
        return decodedJWT.getClaims();
    }



}
