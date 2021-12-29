package com.iscas.biz.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swagger配置
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/08/28
 * @since jdk1.8
 */
@Configuration
@EnableOpenApi
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Lazy(value = false)
public class Swagger3Config {
    @Value("${swagger.enable: true}")
    private boolean swaggerEnable;

    private String version = "1.0";

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("默认")
                .apiInfo(defaultApiInfo())
                .enable(swaggerEnable)
                .securitySchemes(List.of(tokenScheme()))
                .securityContexts(List.of(tokenContext()))
//                .globalOperationParameters(setHeaderToken())
                .select()
                // 自行修改为自己的包路径
//                .apis(RequestHandlerSelectors.basePackage("com.iscas.biz"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()/*.forCodeGeneration(true)*/;

    }


//


    @Bean
    public Docket authApi() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("权限相关")
                .apiInfo(new ApiInfoBuilder().title("权限相关-API")
                        .description("权限相关API")
                        .version(version).build())
                .enable(swaggerEnable)
                .securitySchemes(List.of(tokenScheme()))
                .securityContexts(List.of(tokenContext()))
//                .globalOperationParameters(setHeaderToken())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.iscas.biz.controller.common.auth"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo defaultApiInfo() {
        return new ApiInfoBuilder()
                .title("newframe-接口文档")
                .description("基于swagger3的在线接口文档，如不喜欢此风格，可尝试使用http://<IP:PORT>/<context-path>/doc.html")
                //服务条款网址
                //.termsOfServiceUrl("http://blog.csdn.net/forezp")
                .version(version)
                //.contact(new Contact("帅呆了", "url", "email"))
                .build();
    }

    private HttpAuthenticationScheme tokenScheme() {
//        return new HttpAuthenticationScheme("Authorization", "token验证", "http",
//                "", null, null);
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }

    private SecurityContext tokenContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(SecurityReference.builder()
                        .scopes(new AuthorizationScope[0])
                        .reference("Authorization")
                        .build()))
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
    }

//    private List<Parameter> setHeaderToken() {
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        parameterBuilder.name("Authorization")
//                .description("token")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false);
//        pars.add(parameterBuilder.build());
//        return pars;
//    }


}
