package com.cresters.notification.validation.implementation;


import com.cresters.notification.repository.BaseRepository;
import com.cresters.notification.validation.IsExist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@Component
public class IsExistValidator implements ConstraintValidator<IsExist, Object> {

    private final BaseRepository repo;
    private String tableName;
    private String columnName;
    private boolean required;

    @Autowired
    public IsExistValidator(BaseRepository repo) {
        this.repo = repo;
    }

    @Override
    public void initialize(IsExist annotation) {
        tableName = annotation.tableName();
        columnName = annotation.columnName();
        required = annotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {

        if ((!required && (value == null || StringUtils.isBlank(value.toString())))) {
            return true;
        }

        if (required && (value == null || StringUtils.isBlank(value.toString()))) {
            return false;
        }


        return repo.isExist(tableName, columnName, value);
    }
}