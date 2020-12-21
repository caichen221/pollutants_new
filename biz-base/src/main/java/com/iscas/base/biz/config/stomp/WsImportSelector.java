package com.iscas.base.biz.config.stomp;

import com.iscas.base.biz.aop.enable.EnableWebsocketStomp;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;


/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:43
 * @since jdk1.8
 */
public class WsImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableWebsocketStomp.class.getName(), false));
        WsPushType pushType = attributes.getEnum("pushType");
        switch (pushType) {
            case SIMPLE: {
                return new String[] {WebSocketStompSimpleConfig.class.getName()};
            }
            case RABBITMQ: {
                return new String[] {WebSocketStompProxyRabbitmqConfig.class.getName()};
            }
            default: {
                return new String[] {WebSocketStompSimpleConfig.class.getName()};
            }
        }

    }
}
