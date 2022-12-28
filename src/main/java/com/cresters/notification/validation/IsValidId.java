package com.cresters.notification.validation;


import com.cresters.notification.validation.implementation.IsValidIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author stephen.obi
 */
@Constraint(validatedBy = IsValidIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IsValidId {

    String tableName();
    
    boolean ignoreZero() default false;
    
    String message() default "Invalid Id";

    boolean merchantContext() default false;

     Class<?>[] groups() default {};

     Class<? extends Payload>[] payload() default {};
}
