package com.iscas.base.biz.util;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.base.biz.config.Constants;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.BaseRuntimeException;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/9 16:53
 * @since jdk1.8
 */
public class CustomSession {
    private CustomSession() {}

    private static String getSessionId() {
        String token = SpringUtils.getRequest().getHeader(Constants.TOKEN_KEY);
        if (token == null) {
            //尝试从cookie中拿author
            Cookie cookie = CookieUtils.getCookieByName(SpringUtils.getRequest(), Constants.TOKEN_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        String sessionId = null;
        try {
            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(token);
            Claim claim = stringClaimMap.get("sessionId");
            sessionId = claim.asString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseRuntimeException("校验token信息出错");
        }
        return sessionId;
    }

    public static void setAttribute(String key, Object value) {
        String sessionId = getSessionId();
        Map<String, Object> sessionMap = SessionCacheUtils.get(sessionId);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<>(2 << 2);
        }
        sessionMap.put(key, value);
        SessionCacheUtils.put(sessionId, sessionMap);
    }

    public static void setAttribute(String sessionId, String key, Object value) {
        Map<String, Object> sessionMap = SessionCacheUtils.get(sessionId);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<>(2 << 2);
        }
        sessionMap.put(key, value);
        SessionCacheUtils.put(sessionId, sessionMap);
    }

    public static Object getAttribute(String key) {
        String sessionId = getSessionId();
        Map<String, Object> sessionMap = SessionCacheUtils.get(sessionId);
        if (sessionMap != null) {
            Object o = sessionMap.get(key);
            return o;
        }
        return null;
    }
}
