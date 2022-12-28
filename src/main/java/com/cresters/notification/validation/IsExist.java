package com.cresters.notification.validation;


import com.cresters.notification.validation.implementation.IsExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsExistValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsExist {

    String tableName();

    String columnName();

    boolean shouldBeUnique() default false;

    boolean required() default false;

    String message() default "Value Not Found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
