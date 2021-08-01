package com.iscas.common.tools.listener;

import java.util.EventListener;

/**
 * 自定义监听接口
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/31 17:25
 * @since jdk1.8
 */
@FunctionalInterface
public interface CustomEventListener<E extends CustomEvent> extends EventListener {
    void handleEvent(E e);
}
