package com.cresters.notification.validation.implementation;


import com.cresters.notification.validation.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author stephen.obi
 */
@Slf4j
@Component
public class CustomUrlValidator implements ConstraintValidator<Url, String> {

    Pattern regex = Pattern.compile("\\b(?:(https?|ftp|file)://|www\\.)?[-A-Z\\d+&#/%?=~_|$!:,.;]*[A-Z\\d+&@#/%=~_|$]\\.[-A-Z\\d+&@#/%?=~_|$!:,.;]*[A-Z\\d+&@#/%=~_|$]", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return regex.matcher(value).matches();
        /* *  return UrlValidator.getInstance().isValid(value); */
        //value.matches("^((https?|ftp|smtp|http)://)?(www.)?[a-z0-9.\\-_]+\\.[a-z]+(/[a-zA-Z0-9#]+/?)*$");
    }

    @Override
    public void initialize(Url constraintAnnotation) {
    }
}