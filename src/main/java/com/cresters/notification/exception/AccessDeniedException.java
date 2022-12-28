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
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Access Denied, You don’t have permission to access this resource")
public class AccessDeniedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1400867687060015588L;

    public AccessDeniedException(String message) {
        super(message);
    }
}