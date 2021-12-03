package com.iscas.base.biz.aop.norepeat.submit;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.norepeat.submit.NoRepeatSubmitBean;
import com.iscas.base.biz.util.AuthUtils;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.BaseRuntimeException;
import com.iscas.templet.exception.RepeatSubmitException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:11
 * @since jdk1.8
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAspect implements Constants {
    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut(value = "@annotation(com.iscas.base.biz.aop.norepeat.submit.NoRepeatSubmit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {

        NoRepeatSubmitBean noRepeatSubmitBean = null;
        try {
            noRepeatSubmitBean = applicationContext.getBean(NoRepeatSubmitBean.class);
        } catch (Exception e) {
            log.warn("获取NoRepeatSubmitBean出错", e);
        }
        String token = AuthUtils.getToken();
        HttpServletRequest request = SpringUtils.getRequest();
        String key;
        if (token == null) {
            //如果token为空，使用HttpSession的ID
            token = request.getSession().getId();
        }
        key = token + "->" + request.getRequestURI();
        if (noRepeatSubmitBean != null) {
            switch (noRepeatSubmitBean.getLockType()) {
                case JVM: {
                    synchronized (key.intern()) {
                        if (CaffCacheUtils.get(key) != null) {
                            throw new RepeatSubmitException("重复提交的请求", String.format("请求：[%s]，重复提交", request.getRequestURI()));
                        }
                        CaffCacheUtils.set(key, new Object());
                    }
                    break;
                }
                case REDIS: {
                    INoRepeatSubmitRedisHandler redisHandler = applicationContext.getBean(INoRepeatSubmitRedisHandler.class);
                    if (!redisHandler.check(key)) {
                        throw new RepeatSubmitException("重复提交的请求", String.format("请求：[%s]，重复提交", request.getRequestURI()));
                    }
                    break;
                }
                default: throw new RepeatSubmitException(MessageFormat.format("不支持的类型:[{0}]", noRepeatSubmitBean.getLockType()));
            }
        }

        try {
            Object obj = joinPoint.proceed();
            removeKey(noRepeatSubmitBean, key);
            return obj;
        } catch (RepeatSubmitException e) {
            throw e;
        } catch (Throwable e) {
            removeKey(noRepeatSubmitBean, key);
            throw e;
        }
    }


    private void removeKey(NoRepeatSubmitBean noRepeatSubmitBean, String key) {
        if (noRepeatSubmitBean != null && key != null) {
            switch (noRepeatSubmitBean.getLockType()) {
                case JVM: {
                    synchronized (key.intern()) {
                        CaffCacheUtils.remove(key);
                    }
                    break;
                }
                case REDIS: {
                    applicationContext.getBean(INoRepeatSubmitRedisHandler.class).remove(key);
                    break;
                }
            }
        }
    }

}
