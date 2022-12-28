package com.cresters.notification.validation;


import com.cresters.notification.validation.implementation.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stephen.obi
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PhoneNumber {
    String message() default "Invalid phone number";

    @SuppressWarnings("unused") Class<?>[] groups() default {};

    @SuppressWarnings("unused") Class<? extends Payload>[] payload() default {};
}
