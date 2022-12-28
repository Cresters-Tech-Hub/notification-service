package com.cresters.notification.exception;



import com.cresters.notification.domain.response.AppResponse;
import com.cresters.notification.domain.response.ErrorModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    //AuthorizationResponse
    @ExceptionHandler({AuthorizationResponse.class})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AppResponse<?> handleAuthorizationResponse(final AuthorizationResponse ex) {
        return AppResponse.<Map<String, Object>>builder().data(ex.getResponse()).status(HttpStatus.CREATED.value()).message(ex.getMessage())
                .error("").build();
    }

    @ExceptionHandler({ModelNotFoundException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public AppResponse<?> handleUserNotFoundException(final Exception ex) {
        return AppResponse.<String>builder().data("").status(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
                .error(ex.getMessage()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> handleUserBadRequestException(final BadRequestException ex) {
        return AppResponse.<String>builder().data("").status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
                .error(ex.getMessage()).build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> handleInvalidFormatException(final InvalidFormatException ex) {
        return AppResponse.<String>builder().data("").status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
                .error(ex.getMessage()).build();
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> handleHttpMediaTypeNotAcceptableException(final HttpMediaTypeNotAcceptableException ex) {

        return AppResponse.<String>builder().data("").status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
                .error("ex.getMessage()").build();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(ProcessorException.class)
    @ResponseBody
    public AppResponse<?> processorException(final ProcessorException ex) throws JsonProcessingException {
        ex.printStackTrace();
        return AppResponse.builder().data(new ObjectMapper().readValue(ex.getMessage(), Map.class)).status(ex.getStatus()).message(ex.getMessage()).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public AppResponse<?> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        return AppResponse.<String>builder().data("").status(HttpStatus.METHOD_NOT_ALLOWED.value()).message(ex.getMessage())
                .error(ex.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public AppResponse<?> handleThrowable(final Throwable ex) {
        ex.printStackTrace();
        return AppResponse.builder().data("").status(HttpStatus.EXPECTATION_FAILED.value()).message(ex.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public AppResponse<?> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {

        log.error("Error: {}; ex.getName() {}; ex.getValue() {}", ex.getMessage(), ex.getName(), ex.getValue());

        return AppResponse.<String>builder().data("").error(ErrorModel.builder().messageError("Invalid Value supplied").fieldName(ex.getName()).rejectedValue(ex.getValue()).build())
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AppResponse<?> handleBindException(final BindException ex) {

        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage())).distinct()
                .collect(Collectors.toList());

        Map<String, String> errorResponse = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage());

        }

        AppResponse<?> response = AppResponse.<String>builder().data("").error(errorResponse)
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();

        /* Added to fix class level validator exception **/
        if (errorMessages.isEmpty() && ex.getBindingResult().getAllErrors().size() > 0) {
            for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
                errorMessages.add(new ErrorModel("not_applicable", "Not Applicable", objectError.getDefaultMessage()));
            }
            response = AppResponse.<String>builder().data("").error(errorMessages)
                    .status(4000).message("Input Validation Error").build();
        }
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage())).distinct()
                .collect(Collectors.toList());

        Map<String, String> errorResponse = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage());

        }

        AppResponse<?> response = AppResponse.<String>builder().data("").error(errorResponse)
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();

        /* Added to fix class level validator exception **/
        if (errorMessages.isEmpty() && ex.getBindingResult().getAllErrors().size() > 0) {
            for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
                errorMessages.add(new ErrorModel("not_applicable", "Not Applicable", objectError.getDefaultMessage()));
            }
            response = AppResponse.<String>builder().data("").error(errorMessages)
                    .status(4000).message("Input Validation Error").build();
        }
        return response;
    }

    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> customMethodArgumentNotValidException(final CustomMethodArgumentNotValidException ex) {

        return AppResponse.<String>builder().data("").error(ex.errorMessages)
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> constraintViolationException(final ConstraintViolationException ex) {

        List<ErrorModel> errorMessages = ex.getConstraintViolations().stream()
                .map(err -> new ErrorModel(err.getPropertyPath().toString(), err.getInvalidValue(), err.getMessage()))
                .distinct().collect(Collectors.toList());

        return AppResponse.builder().data("").status(HttpStatus.BAD_REQUEST.value())
                .message("Constraint Violations Error").error(errorMessages).build();
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AppResponse<?> noResultException(final NoResultException ex) {

        return AppResponse.builder().data("").status(HttpStatus.NOT_FOUND.value())
                .message("The requested record not found!").error(ex.getMessage()).build();
    }

    // AccessDeniedException
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public AppResponse<?> accessDeniedException(final AccessDeniedException ex) {

        return AppResponse.builder().data("").status(HttpStatus.UNAUTHORIZED.value())
                .message("Full authentication is required to access this resource").error(ex.getMessage()).build();
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public AppResponse<?> badCredentialsException(final BadCredentialsException ex) {

        return AppResponse.builder().data("").status(HttpStatus.EXPECTATION_FAILED.value())
                .message("Full authentication is required to access this resource").error(ex.getMessage()).build();
    }


    @ExceptionHandler({ChangePasswordRequiredException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public AppResponse<?> changePasswordRequiredException(final ChangePasswordRequiredException ex) {

        return AppResponse.builder().data("").status(HttpStatus.EXPECTATION_FAILED.value())
                .message("User is required to change password!").error(ex.getMessage()).build();
    }

    @ExceptionHandler(RequestRejectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<?> handleRequestRejectedException(final HttpServletRequest request, final RequestRejectedException ex) {
        log.error("Invalid URL - {}", request.getRequestURI(), ex);
        return AppResponse.builder().data("").status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid URL - " + request.getRequestURI()).error(ex.getMessage()).build();
    }


    @ExceptionHandler(StashClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse<String> stashClientException(final StashClientException ex) {
        log.error("StashClientException [{}] - {}", ex.getStatusCode(), ex.getMessage());
        return AppResponse.<String>builder().data(ex.getStatusCode() + " - " + ex.getMessage()).status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage()).error(ex.getMessage()).build();
    }

    @ExceptionHandler(StashServerException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public AppResponse<String> stashServerException(final StashServerException ex) {
        log.error("StashServerException [{}] - {}", ex.getStatusCode(), ex.getMessage());
        return AppResponse.<String>builder().data(ex.getStatusCode() + " - " + ex.getMessage()).status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(ex.getMessage()).error(ex.getMessage()).build();
    }


    @ExceptionHandler(InvalidChecksumException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public AppResponse<Map<?, ?>> invalidChecksumException(final InvalidChecksumException ex) {
        log.error("InvalidChecksumException {} - [{}] - {}", ex.getTable(), ex.getTableId(), ex.getMessage());
        return AppResponse.<Map<?, ?>>builder().data(
                        ImmutableMap.of("table", ex.getTable(), "tableId", ex.getTableId(), "failedChecksum", ex.getMessage())
                ).status(HttpStatus.EXPECTATION_FAILED.value())
                .message(ex.getMessage()).error(ex.getMessage()).build();
    }
}