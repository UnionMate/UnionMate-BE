package com.unionmate.backend.global.apis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.unionmate.backend.exception.CommonErrorInfo;
import com.unionmate.backend.exception.ErrorInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@JsonSerialize
@Builder
@Getter
@ToString
public class ApiData<T> {

  private static final int SUCCESS_CODE = 0;
  private static final String SUCCESS_MESSAGE = "요청에 성공했습니다.";

  @JsonIgnore
  private HttpStatus httpStatus;

  @JsonIgnore
  private final List<ApiHeader> headers = new ArrayList<>();

  @JsonIgnore
  private MediaType contentType;

  private Boolean success;
  private T data;
  private int code;
  private Object message;

  public static <T> ApiData<T> ok(T data) {
    return ApiData.ok(data, MediaType.APPLICATION_JSON);
  }

  public static <T> ApiData<T> ok(T data, MediaType contentType) {
    return ApiData.<T>builder()
        .httpStatus(HttpStatus.OK)
        .success(true)
        .data(data)
        .contentType(contentType)
        .code(SUCCESS_CODE)
        .message(SUCCESS_MESSAGE)
        .build();
  }

  public static <T> ApiData<T> created(T data) {
    return ApiData.created(data, MediaType.APPLICATION_JSON);
  }

  public static <T> ApiData<T> created(T data, MediaType contentType) {
    return ApiData.<T>builder()
        .httpStatus(HttpStatus.CREATED)
        .success(true)
        .data(data)
        .contentType(contentType)
        .code(SUCCESS_CODE)
        .message(SUCCESS_MESSAGE)
        .build();
  }

  public static <T> ApiData<T> from(HttpStatus httpStatus, T data) {
    return ApiData.from(httpStatus, data, MediaType.APPLICATION_JSON);
  }

  public static <T> ApiData<T> from(HttpStatus httpStatus, T data, MediaType contentType) {
    return ApiData.<T>builder()
        .httpStatus(httpStatus)
        .success(true)
        .data(data)
        .contentType(contentType)
        .code(SUCCESS_CODE)
        .message(SUCCESS_MESSAGE)
        .build();
  }

  public static ApiData<Void> noContent() {
    return ApiData.<Void>builder().httpStatus(HttpStatus.NO_CONTENT).build();
  }

  public static ApiData<Map<?, ?>> error(ErrorInfo errorInfo, HttpStatus httpStatus) {
    return ApiData.error(errorInfo, httpStatus, MediaType.APPLICATION_JSON);
  }

  private static ApiData<Map<?, ?>> error(ErrorInfo errorInfo, HttpStatus httpStatus,
      MediaType contentType) {
    return ApiData.<Map<?, ?>>builder()
        .httpStatus(httpStatus)
        .success(false)
        .data(new HashMap<>())
        .code(errorInfo.getCode())
        .message(errorInfo.getMessage())
        .contentType(contentType)
        .build();
  }

  public static ResponseEntity<ApiData<Map<?, ?>>> validationFailure(List<FieldError> fieldErrors) {
    return ApiData.validationFailure(fieldErrors, MediaType.APPLICATION_JSON);
  }

  private static ResponseEntity<ApiData<Map<?, ?>>> validationFailure(List<FieldError> fieldErrors,
      MediaType contentType) {
    Map<String, String> errors = new HashMap<>();
    fieldErrors.forEach(
        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

    ApiData<Map<?, ?>> apiData = ApiData.<Map<?, ?>>builder()
        .success(false)
        .data(errors)
        .message(CommonErrorInfo.NOT_VALID_REQUEST_FIELDS.getMessage())
        .contentType(contentType)
        .httpStatus(HttpStatus.BAD_REQUEST)
        .code(CommonErrorInfo.NOT_VALID_REQUEST_FIELDS.getCode())
        .build();
    return ResponseEntity.ok(apiData);
  }
}
