package com.cresters.notification.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 06/06/2022 01:56
 */


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@DynamicUpdate
public abstract class Auditable<U> implements Serializable {

    /**
     *
     */
    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    protected U createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    protected Date createdDate;
    /**
     * U lastModifiedBy
     */
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", nullable = false)
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    protected Date lastModifiedDate;

    @Version
    @Column(name = "VERSION")
    protected Long version;

    @Column(name = "STATUS")
    @Builder.Default
    protected Integer status = 1;

    @Column(name = "AUTHORIZED")
    @Builder.Default
    protected Integer authorized = 0;
}
