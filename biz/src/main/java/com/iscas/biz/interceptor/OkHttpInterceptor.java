package com.iscas.biz.interceptor;

import com.iscas.base.biz.service.common.OkHttpCustomClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/8/1 17:38
 * @since jdk1.8
 */
@Slf4j
public class OkHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log.debug("------okhttp调用前拦截-----------");
        Response response = chain.proceed(request);
        log.debug("------okhttp调用后拦截-----------");
        return response;
    }

}
