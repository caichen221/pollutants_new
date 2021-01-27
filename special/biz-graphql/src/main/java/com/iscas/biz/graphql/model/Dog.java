package com.iscas.biz.graphql.model;

import lombok.Data;

/**
 * 为演示GraphQL interface创建的实现实体
 */
@Data
public class Dog implements Animal {

    private String name;
    private int legs;
}
