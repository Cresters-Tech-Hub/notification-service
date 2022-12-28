package com.cresters.notification.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Stephen Obi <stephen@credocentral.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 31/08/2022 07:37
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUser {

    private Long user_id;
    private String first_name;
    private String last_name;
    private String name;
    private String client_id;
    private String user_email;
    private String user_name;
    private Integer force_password_change;
    private Integer is_two_factor_required;
    private Long two_factor_type;
    private List<AuthBusiness> businesses;
    private int type;
    private Integer status;
    private List<String> authorities;
}
