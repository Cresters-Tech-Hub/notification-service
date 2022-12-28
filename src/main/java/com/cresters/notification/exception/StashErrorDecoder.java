package com.cresters.notification.exception;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static feign.FeignException.errorStatus;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 03/02/2022 01:29
 */

@Slf4j
public class StashErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        String errorMessage = null;
        Reader reader = null;
        try {

            reader = response.body().asReader(StandardCharsets.UTF_8);
            errorMessage = IOUtils.toString(reader);
            log.error("errorMessage: {}", errorMessage);
        } catch (Throwable e) {
            log.error("IO Exception on reading exception message feign client" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message feign client" + e);
            }
        }

        if(response.status() == 504){
            return new RetryableException( response.status(), errorMessage, response.request().httpMethod(), errorStatus(methodKey, response), null, response.request());
        }

        if (response.status() >= 400 && response.status() <= 499) {
            return new StashClientException(response.status(), errorMessage);
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new StashServerException(response.status(), errorMessage);
        }
        return errorStatus(methodKey, response);
    }

}
