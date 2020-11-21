package com.iscas.biz.jf;

import com.iscas.biz.jf.listener.StartedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/5/10 20:45
 * @since jdk1.8
 */
@SpringBootApplication
public class JFApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JFApp.class);
        springApplication.addListeners(new StartedListener());
        springApplication.run(args);
    }
}
