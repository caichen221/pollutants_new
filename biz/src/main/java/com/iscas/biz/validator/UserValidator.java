package com.iscas.biz.validator;

import com.iscas.biz.validator.anno.UserConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/12/6 14:31
 * @since jdk1.8
 */
public class UserValidator implements ConstraintValidator<UserConstraint, Map<String, Object>> {
    @Override
    public boolean isValid(Map<String, Object> user, ConstraintValidatorContext context) {
        if (!user.containsKey("user_name")) {
            return false;
        }
        String userName = (String) user.get("user_name");
        if (StringUtils.isBlank(userName) || userName.length() < 2 || userName.length() > 20) {
            return false;
        }
        return true;
    }
}
