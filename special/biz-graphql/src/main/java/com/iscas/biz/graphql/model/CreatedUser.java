package com.iscas.biz.graphql.model;

import com.iscas.biz.graphql.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreatedUser implements CreateUserResult {
    private User user;
}
