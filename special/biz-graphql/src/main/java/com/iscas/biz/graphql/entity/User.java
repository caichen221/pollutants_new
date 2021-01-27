package com.iscas.biz.graphql.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint", nullable = false)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String pwd;
}
