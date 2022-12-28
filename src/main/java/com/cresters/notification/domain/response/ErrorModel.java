package com.cresters.notification.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stephen.obi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {

    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}