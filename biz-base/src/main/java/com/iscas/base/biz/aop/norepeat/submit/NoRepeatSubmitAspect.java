package com.iscas.base.biz.aop.norepeat.submit;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.norepeat.submit.NoRepeatSubmitBean;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.BaseRuntimeException;
import com.iscas.templet.exception.RepeatSubmitException;
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

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:11
 * @since jdk1.8
 */
@Aspect
@Component
public class NoRepeatSubmitAspect implements Constants {
    @Autowired
    private  ApplicationContext applicationContext;
    @Pointcut(value = "@annotation(com.iscas.base.biz.aop.norepeat.submit.NoRepeatSubmit)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {

        NoRepeatSubmitBean noRepeatSubmitBean = null;
        try {
            noRepeatSubmitBean = applicationContext.getBean(NoRepeatSubmitBean.class);
        } catch (Exception e) {

        }
        HttpServletRequest request = SpringUtils.getRequest();
        String key = null;
        if (request != null) {
            String token = request.getHeader(TOKEN_KEY);
            if (token == null) {
                //尝试从cookie中拿author
                Cookie cookie = CookieUtils.getCookieByName(request, TOKEN_KEY);
                if (cookie != null) {
                    token = cookie.getValue();
                } else {
                    HttpSession session = request.getSession();
                    token = session.getId();
                }
                key = token + "->" + request.getRequestURI();
                if (noRepeatSubmitBean != null) {
                    switch (noRepeatSubmitBean.getLockType()) {
                        case JVM: {
                            synchronized (key.intern()) {
                                Object obj = CaffCacheUtils.get(key);
                                if (obj != null) {
                                    throw new RepeatSubmitException("重复提交的请求", String.format("请求：[%s]，重复提交", request.getRequestURI()));
                                } else {
                                    CaffCacheUtils.set(key, new Object());
                                }
                            }
                            break;
                        }
                        case REDIS: {
                            INoRepeatSubmitRedisHandler redisHandler = applicationContext.getBean(INoRepeatSubmitRedisHandler.class);
                            boolean flag = redisHandler.check(key);
                            if (!flag) {
                                throw new RepeatSubmitException("重复提交的请求", String.format("请求：[%s]，重复提交", request.getRequestURI()));
                            }
                        }
                        default: {
                            break;
                        }
                    }
                }
            }

        }
        try {
            Object obj =  joinPoint.proceed();
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
                case JVM:{
                    synchronized (key.intern()) {
                        CaffCacheUtils.remove(key);
                    }
                    break;
                }

                case REDIS:{
                    INoRepeatSubmitRedisHandler redisHandler = applicationContext.getBean(INoRepeatSubmitRedisHandler.class);
                    redisHandler.remove(key);
                }
                default:{
                    break;
                }
            }
        }
    }

}
