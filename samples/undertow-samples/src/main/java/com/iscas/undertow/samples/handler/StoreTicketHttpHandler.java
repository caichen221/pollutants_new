package com.iscas.undertow.samples.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.util.Objects;

/**
 * Http方式处理storeTicket
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/14 11:08
 * @since jdk1.8
 */
public class StoreTicketHttpHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        HttpString requestMethod = exchange.getRequestMethod();
        if (!Objects.equals("PUT", requestMethod.toString()) && !Objects.equals("OPTIONS", requestMethod.toString())) {
            //todo
            return;
        }
//        InputStream inputStream = exchange.getInputStream();
//        long requestContentLength = exchange.getRequestContentLength();

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json;charset=utf-8");
        exchange.getResponseSender().send("Hello World");
    }
}
