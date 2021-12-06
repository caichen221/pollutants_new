package com.iscas.biz.validator.anno;

import com.iscas.biz.validator.UserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/6 14:21
 * @since jdk1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Constraint(validatedBy = UserValidator.class)
public @interface UserConstraint {
    String message() default "{user.constraint.message}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
