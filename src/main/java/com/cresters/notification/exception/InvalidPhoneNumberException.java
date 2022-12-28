package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid phone number passed has request")
public class InvalidPhoneNumberException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -182569648306836796L;

}