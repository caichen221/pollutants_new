package com.iscas.biz.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin3")
public class Main3Controller {
 
 
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