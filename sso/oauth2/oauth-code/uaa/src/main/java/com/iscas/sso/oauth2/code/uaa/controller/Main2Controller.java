package com.iscas.sso.oauth2.code.uaa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin2")
public class Main2Controller {
 
 
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