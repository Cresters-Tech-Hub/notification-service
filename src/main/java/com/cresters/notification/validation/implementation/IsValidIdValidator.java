package com.cresters.notification.validation.implementation;


import com.cresters.notification.repository.BaseRepository;
import com.cresters.notification.validation.IsValidId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

/**
 * @author stephen.obi
 */
@Slf4j
public class IsValidIdValidator implements ConstraintValidator<IsValidId, Object> {

    private final BaseRepository repo;
    private String tableName;
    private boolean ignoreZero;

    @Autowired
    public IsValidIdValidator(BaseRepository repo) {
        this.repo = repo;
    }

    @Override
    public void initialize(IsValidId annotation) {
        tableName = annotation.tableName();
        ignoreZero = annotation.ignoreZero();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        value = value == null ? 0 : value;

        if (value instanceof Long) {

            if (!ignoreZero && (long) value == 0) {
                return false;
            } else if (ignoreZero && (long) value == 0) {
                return true;
            }
            return repo.isValidId(tableName, (long) value);


        } else if (value instanceof List) {

            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) value;

            if (ids.isEmpty() && !ignoreZero) {
                return false;
            } else if (ids.isEmpty()) {
                return true;
            }
            if (ids.stream().anyMatch(Objects::isNull)) {
                return false;
            }

            return ids.stream().filter(Objects::nonNull).noneMatch((i) -> (!repo.isValidId(tableName, i)));
        }
        return true;
    }
}