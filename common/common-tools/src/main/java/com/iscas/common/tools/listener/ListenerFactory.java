package com.iscas.common.tools.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听管理器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/31 17:33
 * @since jdk1.8
 */
@Slf4j
public class ListenerFactory {
    private ListenerFactory() {
    }

    private static Map<Class, List<CustomEventListener>> listenerMap = new ConcurrentHashMap<>();



    /**
     * 设置一个监听
     */
    public synchronized static void setListener(CustomEventListener eventListener) {
        if (eventListener == null) {
            return;
        }
        CustomListener listener = eventListener.getClass().getAnnotation(CustomListener.class);
        Type[] genericInterfaces = eventListener.getClass().getGenericInterfaces();
        if (genericInterfaces != null && genericInterfaces.length > 0) {
            ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                Class eventClass = (Class) actualTypeArguments[0];
                List<CustomEventListener> eventListeners = listenerMap.get(eventClass);
                if (eventListeners == null) {
                    eventListeners = new ArrayList<>();
                    listenerMap.put(eventClass, eventListeners);
                }
                //如果已存在了，替换，不存在新增
                //先将名字一样的去掉，再添加到集合中
                eventListeners.removeIf(s -> Objects.equals(listener.name(), s.getClass().getAnnotation(CustomListener.class).name()));
                eventListeners.add(eventListener);
                //排序
                Collections.sort(eventListeners, (l1, l2) -> {
                    Class<? extends CustomEventListener> aClass = l1.getClass();
                    Class<? extends CustomEventListener> bClass = l2.getClass();
                    CustomListener annotation1 = aClass.getAnnotation(CustomListener.class);
                    CustomListener annotation2 = bClass.getAnnotation(CustomListener.class);
                    return annotation1.order() < annotation2.order() ? -1 : 1;
                });
            }
        }
    }

    /**
     * 删除一个监听
     */
    public synchronized static void removeListener(String name) {
        listenerMap.forEach((k, v) -> {
            if (v != null) {
                v.removeIf(s -> Objects.equals(name, s.getClass().getAnnotation(CustomListener.class).name()));
            }
        });
    }

    /**
     * 发布一个事件
     */
    public static void publish(CustomEvent event) {
        List<CustomEventListener> listeners = listenerMap.get(event.getClass());
        if (CollectionUtils.isNotEmpty(listeners)) {
            listeners.forEach(l -> l.handleEvent(event));
        } else {
            log.warn("找不到事件:[{}]的监听", event);
        }
    }

}
