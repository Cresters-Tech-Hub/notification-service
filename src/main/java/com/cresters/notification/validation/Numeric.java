package com.cresters.notification.validation;


import com.cresters.notification.validation.implementation.NumericValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NumericValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Numeric {

    String message() default "Invalid numeric value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
