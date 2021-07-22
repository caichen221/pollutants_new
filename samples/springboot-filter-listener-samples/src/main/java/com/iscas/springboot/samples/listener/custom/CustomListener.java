package com.iscas.springboot.samples.listener.custom;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/22 20:44
 * @since jdk1.8
 */
@Component
public class CustomListener {
    @EventListener
    public void listener(MyEvent myEvent) {
        System.out.println(myEvent);
    }
}
