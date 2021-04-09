package com.iscas.sso.oauth2.uaa.filter;//package com.iscas.biz.security.filter;
//
//import com.auth0.jwt.interfaces.Claim;
//import com.iscas.biz.security.util.JWTUtils;
//import com.iscas.biz.security.util.OutputUtils;
//import com.iscas.templet.common.ResponseEntity;
//import com.iscas.templet.exception.ValidTokenException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//
//@SuppressWarnings("ALL")
//@Component
//public class JwtAuthenticationTokenFilter_BACKUP extends OncePerRequestFilter {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
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
//
//        chain.doFilter(request, response);
//    }
//}
//
