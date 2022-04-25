package com.iscas.biz.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * swagger配置
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/08/28
 * @since jdk1.8
 */
@SuppressWarnings("unused")
@Configuration
@EnableOpenApi
@EnableKnife4j
public class Swagger3Config {
    @Value("${swagger.enable: true}")
    private boolean swaggerEnable;

    private final String version = "1.0";

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

}
