package com.iscas.base.biz.config.elasticjob;

import com.iscas.base.biz.aop.enable.EnableElasticJob;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/1 9:41
 * @since jdk1.8
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class OnElasticJob extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnJava.class.getName());
//        ConditionalOnJava.Range range = (ConditionalOnJava.Range) attributes.get("range");
//        JavaVersion version = (JavaVersion) attributes.get("value");

        ConditionMessage.Builder message = ConditionMessage.forCondition("");
        Map<String, Object> beansWithAnnotation = context.getBeanFactory().getBeansWithAnnotation(EnableElasticJob.class);
        boolean match = MapUtils.isNotEmpty(beansWithAnnotation);
        return match ? ConditionOutcome.match(message.foundExactly("EnableElaticJob")) :
                ConditionOutcome.noMatch(message.because("not EnableElaticJob"));
    }
}
