package com.iscas.base.biz.aop.auth;


import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.StaticInfo;
import com.iscas.base.biz.model.auth.Role;
import com.iscas.base.biz.service.AbstractAuthService;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 角色aop
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/18 15:32
 * @since jdk1.8
 */
@Aspect
@Component
@Slf4j
@ConditionalOnBean(AbstractAuthService.class)
public class RequiredRoleAspect implements Constants {

    @Autowired
    private AbstractAuthService authService;


    @Around("@annotation(requiredRole)")
    public Object before(final ProceedingJoinPoint joinPoint, RequiredRole requiredRole) throws Throwable {
        //如果未开启限流注解，不进行方法限流逻辑判断
        if(!StaticInfo.ENABLE_AUTH){
            return joinPoint.proceed();
        }
        //获取request对象
        HttpServletRequest request = SpringUtils.getRequest();
        HttpServletResponse response = SpringUtils.getResponse();
        Role role = null;
        Map<String, Role> roleMap = null;
        roleMap  = authService.getAuth();
        //首先判断Authorization 中有没有上传信息
        String token = null;
        token = request.getHeader(TOKEN_KEY);
        if (token == null) {
            //尝试从cookie中拿author
            Cookie cookie = CookieUtils.getCookieByName(request, TOKEN_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        if (token == null) {
            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                    " :header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
            throw new AuthorizationException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
        }
        //如果token不为null,校验token
        String username = authService.verifyToken(token);
        List<Role> roles = authService.getRoles(username);
        if (CollectionUtils.isNotEmpty(roles)) {
            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                    " :token中携带的用户或其角色信息不存在");
            throw new AuthorizationException("获取角色信息出错", "token中携带的用户或其角色信息不存在");

        }

//        String[] roles = requiredRole.value();
//        if(!ArrayRaiseUtils.contains(roles, roleKey)){
//            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                    " :权限校验失败");
////            OutputUtils.output(response, 403, "权限校验失败",
////                    "权限校验失败，此登录用户不含有:" + Arrays.toString(roles) + "中任意一个角色" );
////            return null;
//            throw new AuthorizationException("权限校验失败", "权限校验失败，此登录用户不含有:\" + Arrays.toString(roles) + \"中任意一个角色");
//
//        }
        return joinPoint.proceed();
    }
}
