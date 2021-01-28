package com.iscas.springboot.samples.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 20:41
 * @since jdk1.8
 */
public class Listener1 implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("======监听器1执行======");
    }
}
