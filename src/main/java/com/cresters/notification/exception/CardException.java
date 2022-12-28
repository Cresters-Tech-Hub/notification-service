package com.cresters.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Stephen Obi <stephen@credocentral.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 05/09/2022 06:58
 */

@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Invalid card credentials")
@EqualsAndHashCode(callSuper = false)
public class CardException extends RuntimeException {
    public CardException(String message) {
        super(message);
    }
}
