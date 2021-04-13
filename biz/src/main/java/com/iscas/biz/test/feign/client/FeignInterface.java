package com.iscas.biz.test.feign.client;
import com.iscas.templet.common.ResponseEntity;
import feign.*;
import okhttp3.MediaType;

import java.util.Map;

public interface FeignInterface {
    @RequestLine("GET /test/feign/remote/t1?name={name}")
    ResponseEntity remoteT1(@Param("name") String username);

    @RequestLine("DELETE /test/feign/remote/t2/{name}")
    ResponseEntity remoteT2(@Param("name") String name);

    @RequestLine("POST /test/feign/remote/t3")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    ResponseEntity remoteT3(Map<String, Object> params);

    @RequestLine("PUT /test/feign/remote/t4")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    ResponseEntity remoteT4(Map<String, Object> params);

    @RequestLine("POST /test/feign/remote/t5")
    @Headers({"Content-Type: application/x-www-form-urlencoded","Accept: application/json"})
    ResponseEntity remoteT5(@QueryMap Map<String, Object> params);

}