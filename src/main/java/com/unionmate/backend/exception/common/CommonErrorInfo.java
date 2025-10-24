package com.unionmate.backend.exception.common;

import com.unionmate.backend.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorInfo implements ErrorInfo {

    // 10000 ~ 10099 에러코드 사용

    // Request Body가 비어있거나, 잘못된 json key로 요청을 보낸 경우
    INVALID_REQUEST("요청 값이 잘못되었거나 비어있습니다.", 7777),

    // Request DTO 필드 검증에 실패했을 경우
    NOT_VALID_REQUEST_FIELDS("요청 필드 검증에 실패했습니다.", 8888),

    // 예상하지 못한 예외 또는 로직 상의 문제가 발생했을 경우 던지는 예외입니다
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다. 잠시후 다시 시도해주세요.", 9999),

    CLIENT_ABORTED("클라이언트가 응답을 중단했습니다.", 9998),

    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다.", 10011),

    INVALID_JWT("잘못된 토큰입니다", 10001),
    EXPIRED_JWT("만료된 토큰입니다", 10002),

    ;

    private final String message;
    private final Integer code;
}
