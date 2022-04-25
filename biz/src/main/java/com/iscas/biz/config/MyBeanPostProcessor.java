package com.iscas.biz.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 自定义BeanPostProcessor
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/12/29
 * @since jdk11/
 */
@SuppressWarnings("unused")
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
//        handleServerEndPoint(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        return bean;
    }

    private void handleServerEndPoint(Object bean) {
        //获取serverEndpoint
        ServerEndpoint serverEndpoint = AnnotationUtils.findAnnotation(bean.getClass(), ServerEndpoint.class);
        if (!Objects.isNull(serverEndpoint)) {
            //设置@ServerEndpoint注解支持继承，相当于注解@Inherited，应对动态代理导致类上的@ServerEndpoint注解丢失
            InvocationHandler h = Proxy.getInvocationHandler(serverEndpoint);
            try {
                Field typeField = h.getClass().getDeclaredField("type");
                typeField.setAccessible(true);
                Field annotationTypeField = Class.class.getDeclaredField("annotationType");
                annotationTypeField.setAccessible(true);
                Object o = annotationTypeField.get(typeField.get(h));
                Field inheritedField = o.getClass().getDeclaredField("inherited");
                this.updateFinalModifiers(inheritedField);
                inheritedField.set(o, true);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("修改@ServerEndPoint注解失败");
            }
        }
    }

    private void updateFinalModifiers(Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
}
