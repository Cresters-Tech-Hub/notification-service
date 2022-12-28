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
 * @since 15/03/2022 06:35
 */


@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "The record you are trying to modify have been tampered with out side of the system")
@EqualsAndHashCode(callSuper = false)
public class InvalidChecksumException extends RuntimeException {

    private static final long serialVersionUID = -167207076249088181L;
    private final String table;
    private final long tableId;

    public InvalidChecksumException(String message, String table, long tableId) {
        super(message);
        this.table = table;
        this.tableId = tableId;
    }
}