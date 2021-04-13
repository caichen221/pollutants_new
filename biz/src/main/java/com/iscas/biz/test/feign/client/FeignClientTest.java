package com.iscas.biz.test.feign.client;

import com.iscas.templet.common.ResponseEntity;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.http.MediaType;

import java.util.HashMap;

public class FeignClientTest {
    public static void main(String[] args) {
        FeignInterface feignInterface = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
//                .decoder(new StringDecoder())
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(FeignInterface.class, "http://localhost:7901/demo");
        ResponseEntity res1 = feignInterface.remoteT1("zhansan");
        System.out.println(res1);
        ResponseEntity res2 = feignInterface.remoteT2("zhansan");
        System.out.println(res2);
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        ResponseEntity res3 = feignInterface.remoteT3(map);
        System.out.println(res3);
        ResponseEntity res4 = feignInterface.remoteT4(map);
        System.out.println(res4);
        ResponseEntity res5 = feignInterface.remoteT5(map);
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