package com.iscas.undertow.samples;

import com.iscas.undertow.samples.handler.StoreTicketHttpHandler;
import io.undertow.Handlers;
import io.undertow.Undertow;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 启动类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 11:13
 * @since jdk1.8
 */
@Slf4j
public class UndertowMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(() -> {
            Undertow server = Undertow.builder()
                    .addHttpListener(8080, "127.0.0.1")
                    .setHandler(Handlers.path().addExactPath("/storeTicket", new StoreTicketHttpHandler()))
                    .build();
            server.start();
        }).start();
    }

}