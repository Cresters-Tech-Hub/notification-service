package com.cresters.notification.util;

import com.cresters.notification.config.AuthUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 16/02/2021 14:28
 */


@SuppressWarnings("NullableProblems")
@Data
@Component
@Slf4j
public class AppConstants implements ApplicationContextAware {

    public static final String APP_CONTEXT = "/";
    private static ApplicationContext context;

    public static Optional<AuthUser> getAuthUser() {
        TokenStore tokenStore = ((TokenStore) context.getBean("tokenStore"));
        ModelMapper modelMapper = ((ModelMapper) context.getBean("modelMapper"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (null != auth && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return Optional.of(modelMapper.map(tokenStore.readAccessToken(((OAuth2AuthenticationDetails) auth.getDetails()).getTokenValue()).getAdditionalInformation(), AuthUser.class));
        }
        return Optional.empty();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        AppConstants.context = context;
    }

    @SuppressWarnings("unused")
    public interface ApiResponseMessage {
        String SUCCESSFUL = "Successfully processed";
        String PENDING = "Pending approval";
        String FAILED = "Failed request";
        String UPDATE = "Successfully updated";
        String GET = "Successfully fetched records";
    }
}