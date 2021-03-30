package com.iscas.sso.oauth2.code.uaa.handler;//package com.iscas.biz.security.handler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// *
// * 授权处理
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2021/03/14 15:09
// * @since jdk1.8
// */
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//    @Autowired
//    UserDetailsService userDetailsService;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String userName = (String) authentication.getPrincipal(); // 这个获取表单输入中返回的用户名;
//        String password = (String) authentication.getCredentials(); // 这个是表单中输入的密码；
//
//        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);
//
//        if (!passwordEncoder.matches(password, userInfo.getPassword())) {
//            throw new BadCredentialsException("用户名密码不正确，请重新登陆！");
//        }
//
//        return new UsernamePasswordAuthenticationToken(userName, password, userInfo.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
