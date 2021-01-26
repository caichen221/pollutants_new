package com.iscas.biz.graphql.model;
 
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Author {

    private String firstName;

    private String lastName;
}