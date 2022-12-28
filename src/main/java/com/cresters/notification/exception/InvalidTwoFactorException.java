package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author stephen.obi
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Invalid 2 factor Token supplied")
public class InvalidTwoFactorException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4915936257141907508L;

    public InvalidTwoFactorException(String message) {
        super(message);
    }
}