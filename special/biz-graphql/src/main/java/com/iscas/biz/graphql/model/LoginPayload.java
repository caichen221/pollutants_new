package com.iscas.biz.graphql.model;

import com.iscas.biz.graphql.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPayload implements LoginResult{
    private Long token;
    private User user;
}
