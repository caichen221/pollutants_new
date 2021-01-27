package com.iscas.biz.graphql;

import com.coxautodev.graphql.tools.SchemaParserDictionary;
import com.iscas.biz.graphql.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/26 17:17
 * @since jdk1.8
 */
@SpringBootApplication
public class GraphqlApp {
    public static void main(String[] args) {
        SpringApplication.run(GraphqlApp.class, args);
    }
    @Bean
    SchemaParserDictionary schemaParserDictionary() {
        return new SchemaParserDictionary()
                .add(CreatedUser.class)
                .add(ErrorContainer.class)
                .add(LoginPayload.class)
                .add(Dog.class)
                .add(Fish.class);
    }
}
