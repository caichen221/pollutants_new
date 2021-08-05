package com.iscas.base.biz.util;

import com.iscas.base.biz.config.stomp.MyWebMvcStompEndpointRegistry;
import com.iscas.base.biz.config.stomp.WebSocketStompSimpleConfig;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebMvcStompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebMvcStompWebSocketEndpointRegistration;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * websocket工具类
 *
 * <p>可以使用ws://19.168.100.88:7901/demo/webSocketServer/xxx/yyy/websocket</p>
 * <p>返回websocketSessions，其中key为yyy,val为对应的websocket session</p>
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/5 21:19
 * @since jdk1.8
 */
public class WebSocketUtils {
    private WebSocketUtils() {}

    private static volatile Map<String, Object> websocketSessionsHolder;

    /**
     * 获取所有的websocket-sessions, Holder形式，不是真正的websoketsession
     * */
    public static Map<String, Object> getSessionsHolder() throws NoSuchFieldException, IllegalAccessException {
        if (websocketSessionsHolder == null) {
            synchronized (WebSocketUtils.class) {
                if (websocketSessionsHolder == null) {
                    WebMvcStompEndpointRegistry endpointRegistry = (WebMvcStompEndpointRegistry) WebSocketStompSimpleConfig.endpointRegistry;
                    Field registrationsField = null;
                    if (endpointRegistry instanceof MyWebMvcStompEndpointRegistry) {
                        //自定义的类型，获取父类的
                        registrationsField = endpointRegistry.getClass().getSuperclass().getDeclaredField("registrations");
                    } else {
                        registrationsField = endpointRegistry.getClass().getDeclaredField("registrations");
                    }
                    registrationsField.setAccessible(true);
                    List<WebMvcStompWebSocketEndpointRegistration> webMvcStompWebSocketEndpointRegistrations = (List<WebMvcStompWebSocketEndpointRegistration>) registrationsField.get(endpointRegistry);
                    WebMvcStompWebSocketEndpointRegistration webMvcStompWebSocketEndpointRegistration = webMvcStompWebSocketEndpointRegistrations.get(0);
                    Field webSocketHandlerField = webMvcStompWebSocketEndpointRegistration.getClass().getDeclaredField("webSocketHandler");
                    webSocketHandlerField.setAccessible(true);
                    SubProtocolWebSocketHandler webSocketHandler = (SubProtocolWebSocketHandler) webSocketHandlerField.get(webMvcStompWebSocketEndpointRegistration);
                    Field field = webSocketHandler.getClass().getDeclaredField("sessions");
                    field.setAccessible(true);
                    websocketSessionsHolder  = (Map<String, Object>) field.get(webSocketHandler);
                }
            }
        }
        return websocketSessionsHolder;
    }

    /**
     * 获取当前所有的websocket-sessions
     * map的key为ws://19.168.100.88:7901/demo/webSocketServer/xxx/yyy/websocket中的yyy
     * */
    public static Map<String, WebSocketSession> getSessions() throws NoSuchFieldException, IllegalAccessException {
        Map<String, WebSocketSession> result = new HashMap<>();
        Map<String, Object> sessionsHolder = getSessionsHolder();
        if (sessionsHolder != null) {
            for (Map.Entry<String, Object> holderEntry : sessionsHolder.entrySet()) {
                String key = holderEntry.getKey();
                Object val = holderEntry.getValue();
                Field sessionField = val.getClass().getDeclaredField("session");
                sessionField.setAccessible(true);
                WebSocketSession session = (WebSocketSession) sessionField.get(val);
                result.put(key, session);
            }
        }
        return result;
    }

    /**
     * 根据key获取WebsocketSession
     * mkey为ws://19.168.100.88:7901/demo/webSocketServer/xxx/yyy/websocket中的yyy
     * */
    public static WebSocketSession getSession(String matchKey) throws NoSuchFieldException, IllegalAccessException {
        Map<String, WebSocketSession> result = new HashMap<>();
        Map<String, Object> sessionsHolder = getSessionsHolder();
        if (sessionsHolder != null) {
            for (Map.Entry<String, Object> holderEntry : sessionsHolder.entrySet()) {
                String key = holderEntry.getKey();
                if (Objects.equals(key, matchKey)) {
                    Object val = holderEntry.getValue();
                    Field sessionField = val.getClass().getDeclaredField("session");
                    sessionField.setAccessible(true);
                    WebSocketSession session = (WebSocketSession) sessionField.get(val);
                    return session;
                }
            }
        }
        return null;
    }

}
