package com.iscas.springboot.samples.listener.custom;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/22 20:41
 * @since jdk1.8
 */
public class MyEvent extends ApplicationEvent {
    private String msg;
    public MyEvent(Object source) {
        super(source);
    }
    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
