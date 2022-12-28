package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested record not found")
public class NotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2645110374972899511L;

    public NotFoundException(String message) {
        super(message);
    }
}