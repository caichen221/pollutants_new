package com.iscas.data.rest.biz.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {


  @Bean
  public GroupedOpenApi datarestusersApi() {
    return GroupedOpenApi.builder()
            .group("datarestusers")
            .pathsToMatch("/datarestusers/**")
            .build();
  }


}