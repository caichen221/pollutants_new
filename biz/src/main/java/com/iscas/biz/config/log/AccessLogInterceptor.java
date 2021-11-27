package com.iscas.biz.config.log;

import com.iscas.base.biz.model.auth.AuthContext;
import com.iscas.base.biz.util.AuthContextHolder;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.biz.model.common.access.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 访问/审计日志
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/29 20:42
 * @since jdk1.8
 */
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {

    /**访问开始时间*/
    private static final String KEY_REQUEST_START_TIME = "KEY_REQUEST_START_TIME";

    /**访问开始时间*/
    private static final String KEY_ACCESS_LOG = "KEY_ACCESS_LOG";

    private String responseHeaderServer;

    public AccessLogInterceptor(String responseHeaderServer) {
        this.responseHeaderServer = responseHeaderServer;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(SpringUtils.getIpAddr())
                .setMethod(request.getMethod())
                .setUri(request.getRequestURI());

        //将信息绑定在request中
        request.setAttribute(KEY_REQUEST_START_TIME, System.currentTimeMillis());
        request.setAttribute(KEY_ACCESS_LOG, accessLog);
        response.addHeader("server", responseHeaderServer);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
        AccessLog accessLog = (AccessLog) request.getAttribute(KEY_ACCESS_LOG);
        Long startTime = (Long) request.getAttribute(KEY_REQUEST_START_TIME);
        if (accessLog != null) {
            Date createTime = new Date();
            AuthContext context = AuthContextHolder.getContext();
            accessLog.setCreateTime(createTime)
                    .setDuration(createTime.getTime() - startTime)
                    .setStatus(response.getStatus())
                    .setUsername(context == null ? null : context.getUsername());
            log.debug(accessLog.toString());
        }
    }
}
