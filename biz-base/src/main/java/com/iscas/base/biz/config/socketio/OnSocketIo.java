package com.iscas.base.biz.config.socketio;

import com.iscas.base.biz.aop.enable.EnableDatasongClientPlus;
import com.iscas.base.biz.aop.enable.EnableSocketio;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/30
 * @since jdk1.8
 */
public class OnSocketIo extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("");
        Map<String, Object> beansWithAnnotation = context.getBeanFactory().getBeansWithAnnotation(EnableSocketio.class);
        boolean match = MapUtils.isNotEmpty(beansWithAnnotation);
        return match ? ConditionOutcome.match(message.foundExactly("EnableSocketio")) :
                ConditionOutcome.noMatch(message.because("not EnableSocketio"));
    }
}
