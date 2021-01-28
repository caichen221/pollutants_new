package com.iscas.springboot.samples.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 20:41
 * @since jdk1.8
 */
@WebListener
public class Listener3 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("======监听器3执行========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
