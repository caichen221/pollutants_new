package com.iscas.sso.oauth2.uaa.config;

import com.iscas.sso.oauth2.uaa.filter.JwtAuthenticationTokenFilter;
import com.iscas.sso.oauth2.uaa.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 参考 ：https://blog.csdn.net/larger5/article/details/81047869
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/27 14:35
 * @since jdk1.8
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    //  未登陆时返回 JSON 格式的数据给前端（否则为 html）
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    // 登录成功返回的 JSON 格式数据给前端（否则为 html）
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    //  登录失败返回的 JSON 格式数据给前端（否则为 html）
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
    private final CustomAccessDeniedHandler accessDeniedHandler;

    // 自定义安全认证
//    private final CustomAuthenticationProvider provider;

    // JWT 拦截器
//    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public WebSecurityConfig(UserDetailsService userDetailsService, CustomAuthenticationEntryPoint authenticationEntryPoint,
                             CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler, CustomLogoutSuccessHandler logoutSuccessHandler, CustomAccessDeniedHandler accessDeniedHandler /*, CustomAuthenticationProvider provider,*/ /*JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter*/) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
//        this.provider = provider;
//        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()//禁用了 csrf 功能
//                .authorizeRequests()//限定签名成功的请求
//                .antMatchers("/decision/**","/govern/**").hasAnyRole("USER","ADMIN")//对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
//                .antMatchers("/admin/login").permitAll()///admin/login 不限定
//                .antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
//                .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
//                .anyRequest().permitAll()//其他没有限定的请求，允许访问
//                .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
//                .and().formLogin()//使用 spring security 默认登录页面
//                .and().httpBasic();//启用http 基础验证
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter(authenticationManager());
        jwtAuthenticationTokenFilter.setUserDetailsService(userDetailsService);

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭session
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .authorizeRequests()
                //.antMatchers("/decision/**","/govern/**").hasAnyRole("USER","ADMIN")//对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
                //.antMatchers("/admin/login").permitAll()///admin/login 不限定
                //.antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
                //.antMatchers("/admin2/**").hasRole("AAA")//对admin2下的接口 需要AAA权限
                .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
                //.antMatchers("/admin3/**").permitAll()//不拦截 admin3 开放的资源



//                .anyRequest()
//                .access("@rbacauthorityservice.hasPermission(request, authentication)")

                .and()
                .formLogin()  //开启登录
                .successHandler(authenticationSuccessHandler) // 登录成功
                .failureHandler(authenticationFailureHandler) // 登录失败
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()

                .and()
                .authorizeRequests()
                .anyRequest().permitAll()//其他没有限定的请求，允许访问
//                .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
//                ;
                .and()
                .addFilter(jwtAuthenticationTokenFilter); // JWT Filter

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(300);

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(provider);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("admin"));
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

