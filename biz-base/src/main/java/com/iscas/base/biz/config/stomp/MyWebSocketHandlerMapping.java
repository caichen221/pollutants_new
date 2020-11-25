package com.iscas.base.biz.config.stomp;

import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.socket.sockjs.support.SockJsHttpRequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 升级springboot到2.4.0后websocket出现跨域问题处理，重写MyWebSocketHandlerMapping
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/25 13:59
 * @since jdk1.8
 */
public class MyWebSocketHandlerMapping extends SimpleUrlHandlerMapping implements SmartLifecycle {

    private volatile boolean running;


    @Override
    protected void initServletContext(ServletContext servletContext) {
        for (Object handler : getUrlMap().values()) {
            if (handler instanceof ServletContextAware) {
                ((ServletContextAware) handler).setServletContext(servletContext);
            }
        }
    }


    @Override
    public void start() {
        if (!isRunning()) {
            this.running = true;
            for (Object handler : getUrlMap().values()) {
                if (handler instanceof Lifecycle) {
                    ((Lifecycle) handler).start();
                }
            }
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            this.running = false;
            for (Object handler : getUrlMap().values()) {
                if (handler instanceof Lifecycle) {
                    ((Lifecycle) handler).stop();
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(Object handler, HttpServletRequest request) {
        Object resolvedHandler = handler;
        if (handler instanceof HandlerExecutionChain) {
            resolvedHandler = ((HandlerExecutionChain) handler).getHandler();
        }
        if (resolvedHandler instanceof CorsConfigurationSource) {
//            if (!this.suppressCors && (request.getHeader(HttpHeaders.ORIGIN) != null)) {

//            }
//            return null;

            if (resolvedHandler instanceof SockJsHttpRequestHandler)  {
                String origin = request.getHeader("Origin");
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(new ArrayList<>(Arrays.asList(origin)));
                config.addAllowedMethod("*");
                config.setAllowCredentials(true);
                config.setMaxAge(365 * 24 * 3600L);
                config.addAllowedHeader("*");
                return config;
            } else {
                return ((CorsConfigurationSource) resolvedHandler).getCorsConfiguration(request);
            }
        }
        return null;
    }
}
