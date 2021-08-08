package com.iscas.biz.test.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.iscas.templet.common.ResponseEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 参考 https://blog.csdn.net/why_still_confused/article/details/108041657
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/8 18:25
 * @since jdk1.8
 */
@RetrofitClient(baseUrl = "http://localhost:7901")
public interface RetrofitApi {
    @GET("/demo/test/retrofit/remote/t1")
    ResponseEntity t1(@Query("name") String name);
}
