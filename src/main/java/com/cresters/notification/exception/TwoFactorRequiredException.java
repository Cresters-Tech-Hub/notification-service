package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author stephen.obi
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "Two factor Token is required")
public class TwoFactorRequiredException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4915936257141907508L;
    private String signature;
    private String twoFactorType;
    private String emailAddress;
    private String phoneNumber;

    public TwoFactorRequiredException(String message) {
        super(message);
    }

    public TwoFactorRequiredException(String message, String signature, String twoFactorType, String emailAddress, String phoneNumber) {
        super(message);
        this.signature = signature;
        this.twoFactorType = twoFactorType;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}