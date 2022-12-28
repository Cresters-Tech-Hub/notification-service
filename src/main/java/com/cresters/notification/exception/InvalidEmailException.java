package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid email passed has request")
@EqualsAndHashCode(callSuper = false)
public class InvalidEmailException extends RuntimeException {

    private static final long serialVersionUID = 3809452529792039330L;

    @SuppressWarnings("unused")
    public InvalidEmailException(String message) {
        super(message);
    }
}