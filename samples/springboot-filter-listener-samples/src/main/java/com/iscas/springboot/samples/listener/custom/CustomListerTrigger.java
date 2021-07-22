package com.iscas.springboot.samples.listener.custom;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/22 20:46
 * @since jdk1.8
 */
@Component
public class CustomListerTrigger implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        event.getApplicationContext().publishEvent(new MyEvent("a", "b"));
    }
}
