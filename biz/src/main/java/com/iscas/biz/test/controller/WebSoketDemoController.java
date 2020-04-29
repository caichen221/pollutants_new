package com.iscas.biz.test.controller;

import com.iscas.templet.common.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 如有要看例子，请打开注释
 *
 **/
//@RestController
@Slf4j
public class WebSoketDemoController {

    //spring提供的发送消息模板
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;


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
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message","服务器主动推的数据");
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
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message","服务器主动推的数据");
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
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message","服务器主动推的数据");
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
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message","服务器主动推的数据");
    }

    /**
     * 接收HttpSession数据
     * */
    @MessageMapping(value = "/httpsession")
    public void httpsession( StompHeaderAccessor accessor) {
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

}
