package com.cresters.notification.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 *
 * @author stephen.obi
 */
@Slf4j
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // your error handling here
        log.error("response code: {}", response.getRawStatusCode());
        log.error(response.getStatusText());
        
        throw new AccessDeniedException("Invalid Username/Password");
    }
}