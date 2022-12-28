package com.cresters.notification.domain.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author stephen.obi
 */
@SuppressWarnings("unused")
public enum Status {
    /*
    0 = disabled,
    1 = enabled
    2 = pending creation authorization
    3 = pending update approval – current status: enabled
    4 = pending update approval – current status: disabled
    5 = Rejected approval
    6 = Pending Banks Approval (Available on CorporateSourceAccount only)
    7 = Pending Banks Update approval (Available on CorporateSourceAccount only)
     */
    DISABLED(0),
    ENABLED(1),
    PENDING(2),
    PENDING_UPDATE_ENABLED(3),
    PENDING_UPDATE_DISABLED(4),
    REJECTED_PENDING(5);


    private final Integer value;

    Status(final Integer newValue) {
        value = newValue;
    }

    private static Optional<Status> valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst();
    }

    public Integer getValue() {
        return value;
    }
}