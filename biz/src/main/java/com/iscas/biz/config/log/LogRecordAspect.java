package com.iscas.biz.config.log;


import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.log.LogRecordConfig;
import com.iscas.base.biz.service.common.SpringService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色aop
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/02/21
 * @since jdk1.8
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect implements Constants {
//    @Autowired
//    private SpringService springService;

    @Around("@annotation(logRecord)")
    public Object around(final ProceedingJoinPoint joinPoint, LogRecord logRecord) throws Throwable {
        //如果未开启日志注解，不进行逻辑
        if(!LogRecordConfig.flag){
            return joinPoint.proceed();
        }
        StringBuilder desc = new StringBuilder();
        IStoreLogService storeLog = SpringService.getApplicationContext().getBean(IStoreLogService.class);
        Map<String, Object> storeMap = new HashMap<>();
        storeMap.put("type", logRecord.type());

        try {
            Object result = joinPoint.proceed();
            desc.append("操作成功:").append(logRecord.desc());
            storeMap.put("desc", desc.toString());
            storeLog.store(storeMap);
            return result;
        } catch (Throwable e) {
            desc.append("操作失败:").append(logRecord.desc());
            storeMap.put("desc", desc.toString());
            storeLog.store(storeMap);
            throw e;
        }

    }
}
