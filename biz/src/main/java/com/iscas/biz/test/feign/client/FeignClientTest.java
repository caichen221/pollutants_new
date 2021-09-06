package com.iscas.biz.test.feign.client;

import com.iscas.biz.test.feign.interceptor.FeignRequestInterceptor;
import com.iscas.templet.common.ResponseEntity;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.HashMap;

public class FeignClientTest {
    public static void main(String[] args) {
        FeignApi feignApi = Feign.builder()
                .requestInterceptor(new FeignRequestInterceptor())
                .encoder(new FormEncoder(new JacksonEncoder()))
                .decoder(new JacksonDecoder())
//                .decoder(new StringDecoder())
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(FeignApi.class, "http://localhost:7901/demo");
        ResponseEntity res1 = feignApi.remoteT1("zhansan");
        System.out.println(res1);
        ResponseEntity res2 = feignApi.remoteT2("zhansan");
        System.out.println(res2);
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        ResponseEntity res3 = feignApi.remoteT3(map);
        System.out.println(res3);
        ResponseEntity res4 = feignApi.remoteT4(map);
        System.out.println(res4);
        ResponseEntity res5 = feignApi.remoteT5(map);
        System.out.println(res5);
    }

//{
//
//
//    public void testLogin()
//    {
//
//        UserAction action = Feign.builder()
////                .decoder(new GsonDecoder())
//                .decoder(new StringDecoder())
//                .options(new Request.Options(1000, 3500))
//                .retryer(new Retryer.Default(5000, 5000, 3))
//                .target(
//                        UserAction.class,
////                        userBase
//                        "http://localhost:8080/"
//                );
//
//        String s = action.loginAction(
//                "zhangsan",
//                "zhangsan"
//        );
//
//        System.out.println("s = " + s);
//
//    }
//
//
//    @Test
//    public void testGetById()
//    {
//
//        UserAction action = Feign.builder()
////                .decoder(new GsonDecoder())
//                .decoder(new StringDecoder())
//                .target(
//                        UserAction.class,
//                        "http://localhost:8080/"
//                );
//
//        String s = action.getById(2);
//
//        System.out.println("s = " + s);
//
//    }
}