package com.iscas.biz.flowable.test;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/21 13:39
 * @since jdk11
 */
@Slf4j
public class ApprovalDemoRequestSuccessDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        log.info("参数：{}", variables);
        //todo 干其他事
    }
}
