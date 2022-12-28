package com.cresters.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -472384034270268277L;

    public ModelNotFoundException(String message) {
        super(message);
    }
}