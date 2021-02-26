package com.iscas.base.biz.filter;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.model.auth.Role;
import com.iscas.base.biz.model.auth.Url;
import com.iscas.base.biz.service.AbstractAuthService;
import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.service.common.SpringService;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.exception.AuthorizationRuntimeException;
import com.iscas.templet.exception.ValidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        String contextPath = request.getContextPath();
        Map<String, Url> urlMap = null;
        Map<String, Role> roleMap = null;
        try {
            urlMap = authService.getUrls();
            roleMap  = authService.getAuth();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                    " :获取角色信息失败", e);

            throw new AuthorizationRuntimeException("获取角色信息失败", "获取角色信息失败");
        }

        Collection<Url> urls = urlMap.values();
        boolean needFlag = false;
        for (Url url : urls) {
            if (pathMatcher.match(contextPath + url.getName(), request.getRequestURI())) {
                needFlag = true;
                break;
            }
        }
        //如果找到匹配的urlx
        if (needFlag) {
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
//                OutputUtils.output(response, 401, "未携带身份认证信息",
//                        "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
//                return;
                throw new AuthorizationRuntimeException("未携带身份认证信息", "header中未携带 Authorization 或未携带cookie或cookie中无Authorization");
            }

            IAuthCacheService authCacheService = SpringService.getApplicationContext().getBean(IAuthCacheService.class);
//            if(CaffCacheUtils.get(token) == null){
            if(authCacheService.get(token) == null){
                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                        " :token有误或已被注销");
                throw new AuthorizationRuntimeException("身份认证信息有误", "token有误或已被注销");
            }
            //如果token不为null,校验token
            String username = null;
            try {
                username = authService.verifyToken(token);
            } catch (ValidTokenException e) {
                e.printStackTrace();
                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                        " :校验token出错");
                throw new AuthorizationRuntimeException("校验身份信息出错", "校验token出错");
            }
            List<Role> roles = authService.getRoles(username);

//            boolean userFlag = authService.validUsername(username);
//            User user = userService.findByUsername(username);

            //如果是超级管理员角色super,直接跳过认证，认为他具有所有权限
            if(roles != null){
                for (Role role1 : roles) {
                    String name = role1.getName();
                    if (Objects.equals(name, Constants.SUPER_ROLE_KEY)) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }

            if (roles == null) {
                log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
                        " :token中携带的用户或其角色信息不存在");
                throw new AuthorizationRuntimeException("用户或其角色信息不存在", "token中携带的用户或其角色信息不存在");
            }
            for (Role role : roles) {
//                if (role == null) {
//                    log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() +
//                            " :未获取到用户角色信息");
//                    throw new AuthorizationRuntimeException("未获取到用户角色信息", "未获取到用户角色信息");
//                }
                List<Url> urlsx = role.getUrls();
                for (Url url : urlsx) {
                    if (pathMatcher.match(contextPath + url.getName(), request.getRequestURI())) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }

            //失败了返回信息
            log.error(request.getRemoteAddr() + "访问" + request.getRequestURI() + " :鉴权失败");
            throw new AuthorizationRuntimeException("鉴权失败");
        }
        filterChain.doFilter(request, response);
    }
}
