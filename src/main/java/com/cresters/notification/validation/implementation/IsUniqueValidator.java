package com.cresters.notification.validation.implementation;


import com.cresters.notification.repository.BaseRepository;
import com.cresters.notification.validation.IsUnique;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

/**
 * @author stephen.obi
 */
@Slf4j
public class IsUniqueValidator implements ConstraintValidator<IsUnique, Object> {

    private String tableName;
    private String columnName;
    private boolean required;
    private boolean shouldBeUnique;

    private final BaseRepository repo;

    public IsUniqueValidator(BaseRepository repo) {
        this.repo = repo;
    }

    @Override
    public void initialize(IsUnique annotation) {
        tableName = annotation.tableName();
        columnName = annotation.columnName();
        required = annotation.required();
        shouldBeUnique = annotation.shouldBeUnique();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {

        if (value instanceof String && !required && StringUtils.isBlank(String.valueOf(value))) {
            return true;
        }else if(!required && isNull(value)) {
            return true;
        }

        return shouldBeUnique && repo.isUnique(tableName, columnName, value);
    }
}