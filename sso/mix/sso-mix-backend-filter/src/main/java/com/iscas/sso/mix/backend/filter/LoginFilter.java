package com.iscas.sso.mix.backend.filter;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.common.web.tools.json.JsonUtils;
import com.iscas.sso.mix.backend.filter.util.JWTUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.AuthorizationException;
import com.iscas.templet.exception.ValidTokenException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 *
 *
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2018/3/21 16:05
 * @Modified:
 **/
//@Slf4j
public class LoginFilter implements Filter {
    private PermissionHandler permissionHandler;
    private String authServerUrl;
    private OkHttpCustomClient okHttpCustomClient = new OkHttpCustomClient();
    public LoginFilter(PermissionHandler permissionHandler, String authServerUrl) {
        this.permissionHandler = permissionHandler;
        this.authServerUrl = authServerUrl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String method = httpServletRequest.getMethod();
        if (Objects.equals(method, "OPTIONS")) {

            String origin = httpServletRequest.getHeader("Origin");
            if (origin == null || "null".equals(origin)) {
                origin = "*";
            }
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            //服务器同意客户端发送cookies
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token,Authorization,Access-Control-Allow-Origin");
            return;
        }
        String token = httpServletRequest.getHeader(Constants.TOKEN_KEY);
        if (token == null) {
            OutputUtils.output((HttpServletResponse) response, 602, "未携带ticket", "未携带ticket");
            return;
        }
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.endsWith("/sso_mix_verify")) {
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", token);
            String result = okHttpCustomClient.doGet(authServerUrl + "verify", header);
            if (result != null) {
                ResponseEntity responseEntity = JsonUtils.fromJson(result, ResponseEntity.class);
                String username = (String) responseEntity.getValue();
                if (username != null) {
                    String token1 = JWTUtils.createToken(username, 1440);
                    ResponseEntity res = new ResponseEntity<>();
                    res.setValue(token1);
                    OutputUtils.output((HttpServletResponse) response, 200, res);
                    return;
                } else {
                    OutputUtils.output((HttpServletResponse) response, 604, responseEntity.getMessage(), responseEntity.getDesc());
                    return;
                }
            } else {
                OutputUtils.output((HttpServletResponse) response, 604, "验证服务校验token出错", "验证服务校验token出错");
                return;
            }
        }
        String username = null;
        try {
            //校验ticket
            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(token);
            username = stringClaimMap.get("username").asString();
        } catch (ValidTokenException e) {
            OutputUtils.output((HttpServletResponse) response, 401, e.getMessage(), e.getMsgDetail());
            return;
        }
        try {
            //校验权限
            permissionHandler.handler(username);
        } catch (AuthorizationException e) {
            OutputUtils.output((HttpServletResponse) response, 403, e.getMessage(), e.getMsgDetail());
            return;
        }

        chain.doFilter(request, response);
    }
}
