package com.iscas.ratelimit.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.iscas.templet.exception.Exceptions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 方法级别限流
 *
 * @author zhuquanwen
 */
@SuppressWarnings({"unused", "UnstableApiUsage"})
@Aspect
@Component
public class MethodRateLimitAspect {
    private final Logger logger = LoggerFactory.getLogger(MethodRateLimitAspect.class);
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**X-FORWARDED-FOR*/
    String X_FORWARDED_FOR = "x-forwarded-for";

    /**Proxy-Client-IP*/
    String PROXY_CLIENT_IP = "Proxy-Client-IP";

    /**WL-Proxy-Client-IP*/
    String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    @Around("@annotation(methodRateLimit)")
    public Object before(final ProceedingJoinPoint joinPoint, MethodRateLimit methodRateLimit) throws Throwable {

        //获取request对象
        String remoteAddr = getRemoteAddr();

        //本地访问不做限制
        if (Objects.equals("127.0.0.1", remoteAddr)) {
            return joinPoint.proceed();
        }
        //获取当前的response对象，如果超过超时时间还得不到令牌，返回服务器繁忙的提示
        //生成一个唯一方法名的key,作为Map的键
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringType().getName();
        String key = className + "." + methodName;

        //如果key不存在或更改了permitsPerSencond 创建一个新的令牌桶，否则返回key对应的值
        RateLimiter rateLimiter = rateLimiterMap.compute(key, (k, v) -> v == null || v.getRate() != methodRateLimit.permitsPerSecond() ?
                RateLimiter.create(methodRateLimit.permitsPerSecond()) : v);
        if (!rateLimiter.tryAcquire(methodRateLimit.maxWait(), TimeUnit.MILLISECONDS)) {
            //获取令牌失败，并且超过超时时间
            logger.warn(remoteAddr + "访问方法:" + key + ",短期无法获取令牌");
            throw Exceptions.requestTimeoutRuntimeException("服务器繁忙,请求超时");
        }
        logger.debug(remoteAddr + "访问方法:" + key + ",获取令牌成功");
        return joinPoint.proceed();
    }


    public  HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attrs -> ((ServletRequestAttributes) attrs).getRequest())
                .orElse(null);
    }

    /**
     * 获取客户端IP地址
     *
     * @return java.lang.String IP地址
     */
    public  String getRemoteAddr() {
        return getRemoteAddr(getRequest());
    }

    /**
     * 获取客户端地址
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public  String getRemoteAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = checkAndGetIpAddr(request, null, X_FORWARDED_FOR);
            ipAddress = checkAndGetIpAddr(request, ipAddress, PROXY_CLIENT_IP);
            ipAddress = checkAndGetIpAddr(request, ipAddress, WL_PROXY_CLIENT_IP);
            if (checkWrongIpAddr(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (Objects.equals("127.0.0.1", ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        System.err.println("获取本机ip地址出错");
                    }
                }
            }
            // "***.***.***.***".length()
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ipAddress = "";
        }
        return ipAddress;
    }

    private  String checkAndGetIpAddr(HttpServletRequest request, String ipAddress, String elseHeaderKey) {
        if (checkWrongIpAddr(ipAddress)) {
            return request.getHeader(elseHeaderKey);
        }
        return ipAddress;
    }

    private  boolean checkWrongIpAddr(String ipAddress) {
        return ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress);
    }
}
