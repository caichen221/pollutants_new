package com.iscas.base.biz.filter;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.model.auth.AuthContext;
import com.iscas.base.biz.model.auth.Role;
import com.iscas.base.biz.model.auth.Url;
import com.iscas.base.biz.service.AbstractAuthService;
import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.util.AuthContextHolder;
import com.iscas.base.biz.util.AuthUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthenticationRuntimeException;
import com.iscas.templet.exception.AuthorizationRuntimeException;
import com.iscas.templet.exception.ValidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 *
 * 继承{@link OncePerRequestFilter} 是为了保证每个请求只执行一次过滤，例如转发不再次执行。
 *
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2018/3/21 16:05
 * @Modified:
 **/
@Slf4j
public class LoginFilter extends OncePerRequestFilter implements Constants {

    private AbstractAuthService authService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public LoginFilter(AbstractAuthService authService){
        this.authService = authService;

    }

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (log.isTraceEnabled()) {
            log.trace("进入 LoginFilter 过滤器");
        }
        String contextPath = request.getContextPath();
        AuthContext authContext = new AuthContext();
        try {
            Map<String, Url> urlMap = null;
//        Map<String, Role> roleMap = null;
            try {
                urlMap = authService.getUrls();
//            roleMap  = authService.getAuth();
            } catch (Exception e) {
//            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                    " :获取角色信息失败", e);
                throw new AuthenticationRuntimeException("获取角色信息失败", "获取角色信息失败");
            }

            Collection<Url> urls = urlMap.values();
            //判断请求的URL是否在配置了权限的URL中
            boolean needFlag = urls.stream().anyMatch(url -> pathMatcher.match(contextPath + url.getName(), request.getRequestURI()));
            //如果找到匹配的urlx
            if (needFlag) {
                String token = AuthUtils.getToken();
                if (token == null) {
//                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                        " :header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
                    throw new AuthenticationRuntimeException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
                }
                authContext.setToken(token);
                IAuthCacheService authCacheService = SpringUtils.getApplicationContext().getBean(IAuthCacheService.class);
                if (authCacheService.get(token) == null) {
//                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                        " :token有误或已被注销");
                    throw new AuthenticationRuntimeException("身份认证信息有误", "token有误或已被注销");
                }
                //如果token不为null,校验token
                String username = null;
                try {
                    username = authService.verifyToken(token);
                    authContext.setUsername(username);
                } catch (ValidTokenException e) {
//                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                        " :校验token出错");
                    throw new AuthenticationRuntimeException("校验身份信息出错", "校验token出错");
                }
                List<Role> roles = authService.getRoles(username);
                authContext.setRoles(roles);

                //如果是超级管理员角色super,直接跳过认证，认为他具有所有权限
                if (roles != null) {
                    if (roles.stream().anyMatch(role1 -> Objects.equals(role1.getName(), Constants.SUPER_ROLE_KEY))) {
                        authContext.setSuper(true);
                        AuthContextHolder.setContext(authContext);
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
                if (roles == null) {
//                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                        " :token中携带的用户或其角色信息不存在");
                    throw new AuthenticationRuntimeException("用户或其角色信息不存在", "token中携带的用户或其角色信息不存在");
                }
                for (Role role : roles) {
                    List<Url> urlsx = role.getUrls();
                    if (!CollectionUtils.isEmpty(urlsx)) {
                        if (urlsx.stream().anyMatch(url -> pathMatcher.match(contextPath + url.getName(), request.getRequestURI()))) {
                            AuthContextHolder.setContext(authContext);
                            filterChain.doFilter(request, response);
                            return;
                        }
                    }
                }

                //失败了返回信息
                throw new AuthorizationRuntimeException("鉴权失败");
            }
            //上面没有报错，或者没有执行doFilter，说明此请求不需要权限
            authContext.setNeedPermission(false);
            AuthContextHolder.setContext(authContext);
            filterChain.doFilter(request, response);
        } finally {
            AuthContextHolder.setContext(authContext);
        }
    }

}
