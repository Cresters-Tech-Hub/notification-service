package com.cresters.notification.exception;

import lombok.Data;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 15/03/2022 05:38
 */


@Data
public class FeignExceptionMessage {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
