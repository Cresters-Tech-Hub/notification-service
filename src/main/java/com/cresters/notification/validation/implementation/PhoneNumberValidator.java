package com.cresters.notification.validation.implementation;


import com.cresters.notification.validation.PhoneNumber;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author stephen.obi
 */
@Component
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches("^234[7-9][0-1]\\d{8}$");
    }
}