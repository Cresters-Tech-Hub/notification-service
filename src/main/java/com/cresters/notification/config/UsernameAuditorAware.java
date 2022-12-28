package com.cresters.notification.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author stephen.obi
 */
public class UsernameAuditorAware implements AuditorAware<String> {

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("System.auto");
        }

        return (authentication.getPrincipal() instanceof UserDetails)
                ? Optional.of(((UserDetails) authentication.getPrincipal()).getUsername())
                : Optional.of(authentication.getPrincipal().toString());

    }
}