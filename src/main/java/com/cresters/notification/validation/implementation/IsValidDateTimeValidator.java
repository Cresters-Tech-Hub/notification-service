package com.cresters.notification.validation.implementation;


import com.cresters.notification.validation.IsValidDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Stephen Obi <stephen@credocentral.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 08/07/2022 21:20
 */


public class IsValidDateTimeValidator implements ConstraintValidator<IsValidDateTime, String> {
    private boolean required;

    @Override
    public void initialize(IsValidDateTime annotation) {
        required = annotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!required && (value == null || value.isEmpty())) {
            return true;
        }

        return (!required || (value != null && !value.isEmpty())) && value.matches("^(\\d{4})-([0-1]\\d)-([0-3]\\d)\\s([0-1]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$");
    }
}
