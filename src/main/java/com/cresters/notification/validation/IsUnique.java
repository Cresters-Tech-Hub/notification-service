package com.cresters.notification.validation;


import com.cresters.notification.validation.implementation.IsUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stephen.obi
 */
@Constraint(validatedBy = IsUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsUnique {

    String tableName();

    String columnName();

    boolean shouldBeUnique() default true;

    boolean required() default true;

    boolean merchantContext() default false;

    String message() default "In use already";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}