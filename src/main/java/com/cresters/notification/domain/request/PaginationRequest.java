package com.cresters.notification.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Stephen Obi <stephen@frontedge.io>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 10/02/2022 10:09
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationRequest {

    @Builder.Default
    @Min(value = 1, message = "Page must be greater than zero (0)")
    @JsonIgnore
    private Integer page = 1;

    @Builder.Default
    @JsonIgnore
    @Max(value = 100, message = "Page size must be greater than 0 but less than 100")
    private Integer size = 25;
}
