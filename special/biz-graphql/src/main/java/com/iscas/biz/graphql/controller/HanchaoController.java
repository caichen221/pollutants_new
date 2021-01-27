package com.iscas.biz.graphql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HanchaoController {

    @GetMapping("/test")
    public String testSpringBoot() {
        return "Hello, springboot";
    }
}
