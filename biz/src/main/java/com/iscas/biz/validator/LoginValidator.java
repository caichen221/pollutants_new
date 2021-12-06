package com.iscas.biz.validator;

import com.iscas.biz.validator.anno.LoginConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/6 14:31
 * @since jdk1.8
 */
public class LoginValidator implements ConstraintValidator<LoginConstraint, Map<String, String>> {
    @Override
    public boolean isValid(Map<String, String> loginInfo, ConstraintValidatorContext context) {
        return loginInfo.containsKey("username") && loginInfo.containsKey("password") && loginInfo.containsKey("key");
    }
}
