package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Password change required")
@EqualsAndHashCode(callSuper = false)
public class ChangePasswordRequiredException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3809452529792039330L;

    public ChangePasswordRequiredException(String message) {
        super(message);
    }
}