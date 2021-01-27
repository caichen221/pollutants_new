package com.iscas.biz.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorContainer implements CreateUserResult,LoginResult {
    private List<String> messages;
}
