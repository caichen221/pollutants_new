package com.iscas.springboot.samples;

import com.iscas.springboot.samples.listener.Listener1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 19:28
 * @since jdk1.8
 */
@SpringBootApplication
@RestController
@ServletComponentScan
public class App {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.addListeners(new Listener1());
        springApplication.run(args);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
