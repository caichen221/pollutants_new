package com.iscas.sso.oauth2.oauthcode.resource.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin4")
public class Main4Controller {
 
 
    @RequestMapping("/")
    public String index(){
 
        return "index" ;
    }
 
    @RequestMapping("/detail")
    public String hello(){
        return "hello" ;
    }
 
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}