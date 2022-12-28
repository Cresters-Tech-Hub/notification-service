package com.cresters.notification.domain.response;

import lombok.Data;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 12/03/2021 12:10
 */
@Data
public abstract class AuditableResponse {

    protected String createdBy;
    protected String createdDate;
    protected String lastModifiedBy;
    protected String lastModifiedDate;
    protected Long version;
    protected Integer status;
    protected Integer authorized;
}
