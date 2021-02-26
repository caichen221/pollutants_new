package com.iscas.biz.service.common;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.stomp.UserAccessor;
import com.iscas.base.biz.model.auth.User;
import com.iscas.base.biz.util.JWTUtils;
import com.iscas.templet.exception.ValidTokenException;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 校验token
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/26 9:26
 * @since jdk1.8
 */
@Component
public class TokenVerifyUserAccessor implements UserAccessor {
    @Override
    public void accessor(Message<?> message, StompHeaderAccessor accessor) {
        Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
        if (raw instanceof Map) {
            //这里就是token
            Object name = ((Map) raw).get(Constants.TOKEN_KEY);
            if (name instanceof List) {
                // 设置当前访问器的认证用户
                String token = ((List) name).get(0).toString();
                String username = null;
                try {
                    Map<String, Claim> claimMap = JWTUtils.verifyToken(token);
                    username = claimMap.get("username").asString();
                    if (username == null) {
                        throw new RuntimeException("websocket认证失败");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new RuntimeException("websocket认证失败", e);
                } catch (ValidTokenException e) {
                    e.printStackTrace();
                    throw new RuntimeException("websocket认证失败", e);
                }
                User user = new User();
                user.setUsername(username);
                accessor.setUser(user);
            }
        }
    }
}
