package com.iscas.biz.security.filter;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.biz.security.util.JWTUtils;
import com.iscas.biz.security.util.OutputUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.ValidTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@SuppressWarnings("ALL")
//@Component
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

//    @Autowired
    private UserDetailsService userDetailsService;
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
//
    private AuthenticationManager authenticationManager;


    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {

        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            //如果没有传递Authorization，传递给下一个过滤器处理
            doFilter(request, response, chain);
            return;
        }
        try {
            if (!JWTUtils.TOKENS.contains(authHeader)) {
                throw new ValidTokenException("token已被删除");
            }
            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(authHeader);
            String username = stringClaimMap.get("username").asString();
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (ValidTokenException e) {
//            throw new RuntimeException(e);
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setMessage("权限校验出错");
            responseEntity.setDesc(e.getMessage());
            OutputUtils.output(response, 403, responseEntity);
            return;
        }

        chain.doFilter(request, response);
    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String authHeader = request.getHeader("Authorization");
//        try {
//            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(authHeader);
//            String username = stringClaimMap.get("username").asString();
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                if (userDetails != null) {
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    return authentication;
//                }
//            }
//
//        } catch (ValidTokenException e) {
////            throw new RuntimeException(e);
//            ResponseEntity responseEntity = new ResponseEntity();
//            responseEntity.setMessage("登录出错");
//            responseEntity.setDesc(e.getMessage());
//            OutputUtils.output(response, 403, responseEntity);
//        }
//        return null;
//
////        String username = request.getParameter("username");
////        UserJoinTimeAuthentication usernamePasswordAuthenticationToken =new UserJoinTimeAuthentication(username);
////        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
////        if (authentication != null) {
////            super.setContinueChainBeforeSuccessfulAuthentication(true);
////        }
////        return authentication;
//    }
}

