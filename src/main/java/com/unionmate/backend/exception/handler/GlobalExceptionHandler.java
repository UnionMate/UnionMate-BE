package com.unionmate.backend.exception.handler;


import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.unionmate.backend.exception.ApplicationException;
import com.unionmate.backend.exception.common.CommonErrorInfo;
import com.unionmate.backend.global.apis.ApiData;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public ApiData<Map<?, ?>> handleApplicationException(ApplicationException e) {
    if (e.getHttpStatus().is4xxClientError()) {
      log.info(e.getMessage());
    } else {
      log.warn("[Server error] \"{}\" Occurred", e.getMessage(), e);
    }

    return ApiData.error(e.getErrorInfo(), e.getHttpStatus());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ApiData<Map<?, ?>> handleAnyUnexpectedException(Exception e, HttpServletRequest req) {

    log.error("[Unexpected Error] \"{}\" Occurred", e.getMessage(), e);
    return ApiData.error(CommonErrorInfo.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiData<Map<?, ?>>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e,
      HttpServletRequest request
  ) {
    String errorMessages = e.getBindingResult().getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .collect(Collectors.joining(",\n"));

    String requestInfo = String.format("[%s] %s", request.getMethod(), request.getRequestURI());

    log.info("Validation failed for request {}: [{}]", requestInfo, errorMessages);
    return ApiData.validationFailure(e.getFieldErrors());
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ApiData<Map<?, ?>> handleNoResourceFoundException(NoResourceFoundException e,
      HttpServletRequest request) {
    log.debug(e.getMessage());
    return ApiData.error(CommonErrorInfo.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ApiData<Map<?, ?>> handleHttpRequestNotSupportedException(
      HttpRequestMethodNotSupportedException e,
      HttpServletRequest request) {
    log.debug(e.getMessage());
    return ApiData.error(CommonErrorInfo.RESOURCE_NOT_FOUND, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ApiData<Map<?, ?>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    log.debug(e.getMessage());
    return ApiData.error(CommonErrorInfo.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ApiData<Map<?, ?>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.debug(e.getMessage());
    return ApiData.error(CommonErrorInfo.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnrecognizedPropertyException.class)
  public ApiData<Map<?, ?>> handleUnrecognizedPropertyException(
      UnrecognizedPropertyException e,
      HttpServletRequest request
  ) {
    log.debug(e.getMessage());
    return ApiData.error(CommonErrorInfo.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
  }
}
