package com.cresters.notification.config;


import com.cresters.notification.domain.response.AppResponse;
import com.cresters.notification.util.AppConstants;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor {

    private final HttpServletRequest httpRequest;
    private final Gson gson;

    @SuppressWarnings("unchecked")
    @Order(2)
    @PreAuthorize("isAuthenticated()")
    @Around("execution(public *  com.cresters.notification.controller..*(..)))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.nanoTime();
        String username = AppConstants.getAuthUser().isPresent() ? AppConstants.getAuthUser().get().getUser_name() : "Anonymous";

        log.info("Request Body: {}", "/csrf".equals(httpRequest.getRequestURI()) ? joinPoint.getArgs() :
                gson.toJson(joinPoint.getArgs()));

        Collections.list(httpRequest.getHeaderNames()).stream().map(StringEscapeUtils::escapeJava).limit(40)
                .forEach(headerName -> log.debug("Request Header: {} - {}", headerName, StringEscapeUtils.escapeJava(httpRequest.getHeader(headerName))));

        if (httpRequest.getRequestURI().contains("/authorize")) {
            return joinPoint.proceed();
        }
        ResponseEntity<AppResponse<?>> response = (ResponseEntity<AppResponse<?>>) joinPoint.proceed();

        Objects.requireNonNull(response.getBody()).setExecTime((double) (System.nanoTime() - startTime) / 100000000);

        log.info("[Time Elapsed: {}] {} {} - Request from [{}]/{}", ((System.nanoTime() - startTime) / 1000000), httpRequest.getMethod(), httpRequest.getRequestURI(), username,
                httpRequest.getLocalAddr());
        log.debug("Response Body: {}", gson.toJson(response));
        return response;
    }
}