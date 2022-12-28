package com.cresters.notification.validation.implementation;


import com.cresters.notification.validation.Numeric;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || value.isEmpty() || value.matches("\\d+");
    }
}