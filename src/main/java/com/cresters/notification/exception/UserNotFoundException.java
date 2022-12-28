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
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation error")
public class UserNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1686570903518771275L;

    public UserNotFoundException(String message) {
        super(message);
        // this.errorMessages = errorMessages;
    }
}