package com.iscas.biz.test.controller;

import com.iscas.base.biz.util.WebSocketUtils;
import com.iscas.biz.model.common.WsData;
import com.iscas.biz.service.common.WsService;
import com.iscas.templet.common.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.ByteString;
import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * 如有要看例子，请打开注释
 *
 **/
@RestController
@Slf4j
@EnableScheduling
public class WebSoketDemoController {

    @Autowired
    private SimpUserRegistry userRegistry;
    @Autowired
    private WsService wsService;


    /**
     * 接收用户信息
     * */
    @MessageMapping(value = "/principal")
    public void test(Principal principal) {
        log.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        WsData<String> wsData = new WsData<>(UUID.randomUUID().toString(), WsData.MsgTypeEnum.BUSINESS, principal.getName(),
                false, "服务器推送得数据");
        wsService.p2p(wsData);
    }


    /**
     * 接收数据体
    * */
    @MessageMapping(value = "/P2P")
    public void templateTest(Principal principal, Map<String,String> data) {
        log.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        WsData<String> wsData = new WsData<>(UUID.randomUUID().toString(), WsData.MsgTypeEnum.BUSINESS, principal.getName(),
                false, "服务器推送得数据");
        wsService.p2p(wsData);
    }


    /**
     * 接收路径参数
     * */
    @MessageMapping(value = "/path/{name}/{company}")
    public void pathTest(Principal principal, @DestinationVariable String name, @DestinationVariable String company) {
        log.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        WsData<String> wsData = new WsData<>(UUID.randomUUID().toString(), WsData.MsgTypeEnum.BUSINESS, principal.getName(),
                false, "服务器推送得数据");
        wsService.p2p(wsData);
    }

    /**
     * 接收header参数
     * */
    @MessageMapping(value = "/header")
    public void headerTest(Principal principal, @Header String one, @Header String two) {
        log.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        WsData<String> wsData = new WsData<>(UUID.randomUUID().toString(), WsData.MsgTypeEnum.BUSINESS, principal.getName(),
                true, "服务器推送得数据");
        wsService.p2p(wsData);
    }

    /**
     * 接收HttpSession数据
     * */
    @MessageMapping(value = "/httpsession")
    public void httpsession(StompHeaderAccessor accessor) {
        String name = (String) accessor.getSessionAttributes().get("name");
        System.out.println(1111);
    }

//    /**
//     * 接收param数据
//     * */
//    @MessageMapping(value = "/param")
//    public void param(String name) {
//        System.out.println(1111);
//    }

    /*广播*/
    @MessageMapping("/broadcast")
    @SendTo("/topic/getResponse")
    public ResponseEntity topic() throws Exception {
        return new ResponseEntity(200,"success");
    }



    //===================下面两个函数测试直接通过session发送消息==============================

    /**
     * 定时发送测试消息，为测试直接通过session发消息
     * */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void send() throws NoSuchFieldException, IllegalAccessException, IOException {
        WebSocketSession session = WebSocketUtils.getSession("abcde");
        if (session != null) {
            TextMessage message = new TextMessage("测试直接发送session消息");
            session.sendMessage(message);
        }
    }

//    public static void main(String[] args) {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        Request request = new Request.Builder()
//                .url("ws://localhost:7901/demo/webSocketServer/xxx/abcde/websocket").build();
//        client.newWebSocket(request, new WebSocketListener() {
//            @Override
//            public void onOpen(WebSocket webSocket, Response response) {
//                super.onOpen(webSocket, response);
//            }
//
//            @Override
//            public void onMessage(WebSocket webSocket, String text) {
//                log.debug("收到通过session发来的websocket消息：{}", text);
//                super.onMessage(webSocket, text);
//            }
//
//            @Override
//            public void onMessage(WebSocket webSocket, ByteString bytes) {
//                super.onMessage(webSocket, bytes);
//            }
//
//            @Override
//            public void onClosing(WebSocket webSocket, int code, String reason) {
//                super.onClosing(webSocket, code, reason);
//            }
//
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                super.onClosed(webSocket, code, reason);
//            }
//
//            @Override
//            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
//                super.onFailure(webSocket, t, response);
//            }
//        });
//
//
//    }


}
