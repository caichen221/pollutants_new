package com.iscas.base.biz.config.stomp;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebMvcStompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebMvcStompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.util.UrlPathHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 升级springboot到2.4.0后websocket出现跨域问题处理，重写WebMvcStompEndpointRegistry
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/25 13:44
 * @since jdk1.8
 */
public class MyWebMvcStompEndpointRegistry extends WebMvcStompEndpointRegistry {
    public MyWebMvcStompEndpointRegistry(WebSocketHandler webSocketHandler, WebSocketTransportRegistration transportRegistration, TaskScheduler defaultSockJsTaskScheduler) {
        super(webSocketHandler, transportRegistration, defaultSockJsTaskScheduler);
    }

    public AbstractHandlerMapping getHandlerMapping() {
        Map<String, Object> urlMap = new LinkedHashMap<>();
        List<WebMvcStompWebSocketEndpointRegistration> registrations = null;
        try {
            registrations = (List<WebMvcStompWebSocketEndpointRegistration>) ReflectUtil.getFieldValue(this, "registrations");
        } catch (Exception e) {
//            e.printStackTrace();
            throw new StompRegistryException("从WebMvcStompEndpointRegistry中反射获取属性registrations出错", e);
        }
        for (WebMvcStompWebSocketEndpointRegistration registration : registrations) {
            MultiValueMap<HttpRequestHandler, String> mappings = registration.getMappings();
            mappings.forEach((httpHandler, patterns) -> {
                for (String pattern : patterns) {
                    urlMap.put(pattern, httpHandler);
                }
            });
        }
        MyWebSocketHandlerMapping hm = new MyWebSocketHandlerMapping();
        hm.setUrlMap(urlMap);
        int order = 1;
        try {
            order = (int) ReflectUtil.getFieldValue(this, "order");
        } catch (Exception e) {
//            e.printStackTrace();
            throw new StompRegistryException("从WebMvcStompEndpointRegistry中反射获取属性order出错", e);
        }
        hm.setOrder(order);

        UrlPathHelper urlPathHelper = null;
        try {
            urlPathHelper = (UrlPathHelper) ReflectUtil.getFieldValue(this, "urlPathHelper");
        } catch (Exception e) {
//            e.printStackTrace();
            throw new StompRegistryException("从WebMvcStompEndpointRegistry中反射获取属性urlPathHelper出错", e);
        }
        if (urlPathHelper != null) {
            hm.setUrlPathHelper(urlPathHelper);
        }
        return hm;
    }

}
