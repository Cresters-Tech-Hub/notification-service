package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 03/02/2022 01:31
 */

@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid value passed")
@EqualsAndHashCode(callSuper = false)
public class StashClientException extends RuntimeException {

    public final int statusCode;
    /**
     *
     */
    private static final long serialVersionUID = -167207076249088181L;

    public StashClientException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

