package com.iscas.biz.jersey.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 17:34
 * @since jdk1.8
 */

@Configuration
//@ApplicationPath("/jersey")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.iscas.biz.jersey");

        register(CustomJsonMappingExceptionMapper.class);
        register(CustomJsonParseExceptionMapper.class);

        //防止文件上传报错No injection source found for a parameter of type public
        register(MultiPartFeature.class);
//        register(HelloWorldResource.class);
    }
}
