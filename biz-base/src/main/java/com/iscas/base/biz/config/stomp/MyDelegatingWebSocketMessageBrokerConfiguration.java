package com.iscas.base.biz.config.stomp;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;

import java.lang.reflect.Method;

/**
 * 升级springboot到2.4.0后websocket出现跨域问题处理，重写DelegatingWebSocketMessageBrokerConfiguration
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/25 13:12
 * @since jdk1.8
 */
@Configuration
//@Primary
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyDelegatingWebSocketMessageBrokerConfiguration extends DelegatingWebSocketMessageBrokerConfiguration {

//    @Bean
//    @Primary
    public HandlerMapping stompWebSocketHandlerMapping() {
        WebSocketHandler handler = decorateWebSocketHandler(subProtocolWebSocketHandler());
        MyWebMvcStompEndpointRegistry registry = new MyWebMvcStompEndpointRegistry(
                handler, getTransportRegistration(), messageBrokerTaskScheduler());

        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext != null) {
            Method method = ReflectUtil.getMethod(MyWebMvcStompEndpointRegistry.class, "setApplicationContext", ApplicationContext.class);
            ReflectUtil.invoke(registry, method, applicationContext);
//            setApplicationContext(applicationContext);
        }
        registerStompEndpoints(registry);
        return registry.getHandlerMapping();
    }


}
