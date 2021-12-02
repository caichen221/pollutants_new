package com.iscas.base.biz.util;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.model.access.AccessLog;
import com.iscas.base.biz.model.auth.AuthContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/2 9:52
 * @since jdk1.8
 */
public class AccessLogUtils {
    private static Logger accessLogger = LoggerFactory.getLogger("accessLogger");

    private AccessLogUtils() {
    }

    public static void log(HttpServletRequest request, int status) {
        Long startTime = request == null ? null : (Long) request.getAttribute(Constants.KEY_REQUEST_START_TIME);

        Date createTime = new Date();
        AuthContext context = AuthContextHolder.getContext();
        AccessLog accessLog = new AccessLog()
                .setIp(SpringUtils.getIpAddr(request))
                .setMethod(request.getMethod())
                .setUri(request.getRequestURI())
                .setCreateTime(createTime)
                .setDuration(startTime != null ? createTime.getTime() - startTime : null)
                .setStatus(status)
                .setUsername(context == null ? null : context.getUsername());
        accessLogger.info(accessLog.toString());
    }
}
