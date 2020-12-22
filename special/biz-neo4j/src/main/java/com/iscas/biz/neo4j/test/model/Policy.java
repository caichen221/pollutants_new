package com.iscas.biz.neo4j.test.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Node("policy")
public class Policy {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "title")
    private String title;

    //其他属性定义....

    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<Policy> policys = new ArrayList<>();

}