package com.iscas.ratelimit.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.iscas.ratelimit.autoconfigure.RateLimiterProps;
import com.iscas.templet.exception.Exceptions;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RateLimiter 应用总体限流过滤器
 *
 * @author zhuquanwen
 **/
@SuppressWarnings("UnstableApiUsage")
public class RateLimiterFilter extends OncePerRequestFilter {


    private final RateLimiterProps rateLimiterProps;
    private final RateLimiter rateLimiter;
    private final List<String> staticUrls;

    public RateLimiterFilter(RateLimiterProps rateLimiterProps) {
        this.rateLimiterProps = rateLimiterProps;
        this.staticUrls = rateLimiterProps.getStaticUrl();
        rateLimiter = RateLimiter.create(rateLimiterProps.getPermitsPerSecond());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,  HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String contextPath = request.getContextPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        if (!CollectionUtils.isEmpty(staticUrls) && staticUrls.stream().anyMatch(url -> antPathMatcher.match(contextPath + url, request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!rateLimiter.tryAcquire(rateLimiterProps.getMaxWait().toMillis(), TimeUnit.MILLISECONDS)) {
            //获取令牌失败，并且超过超时时间
            throw Exceptions.requestTimeoutRuntimeException("服务器繁忙,请求超时");
        }
        filterChain.doFilter(request, response);
    }
}
