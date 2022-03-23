package com.soon83.springdatajpa.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * E000
     * common error
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E000", "승인되지 않은 요청입니다. (인증필요)"), // 401
    FORBIDDEN(HttpStatus.FORBIDDEN, "E001", "접근권한이 없습니다."), // 403
    NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "요청하신 리소스가 존재하지 않습니다."), // 404
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E003", "지원하지 않는 HTTP 메서드입니다."), // 405
    CONFLICT(HttpStatus.CONFLICT, "E004", "이미 존재하는 데이터 입니다."), // 409

    /**
     * E100
     * http status 400
     */
    BIND_ERROR(HttpStatus.BAD_REQUEST, "E100", "유효하지 않은 파라미터 입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E101", "유효하지 않은 파라미터 입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "E102", "유효하지 않은 파라미터 입니다."), // 파라미터 형변환 불가
    INVALID_JSON_INPUT(HttpStatus.BAD_REQUEST, "E103", "유효하지 않은 JSON 포맷입니다."), // JSON 형식이 알맞지 않음
    BIND_ERROR_ENUM(HttpStatus.BAD_REQUEST, "E104", "유효하지 않은 파라미터 입니다."), // ENUM에 정의되지 않은 코드
    DATA_INTEGRITY_VIOLATION_ERROR(HttpStatus.BAD_REQUEST, "E105", "유효하지 않은 파라미터 입니다."), // 데이터무결성 제약조건 위배

    /**
     * E200
     * 공통코드
     */
    NOT_FOUND_COMMON_CODE(HttpStatus.NOT_FOUND, "E200", "공통코드 정보가 없습니다."),

    /**
     * E300
     * 사용자
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "E300", "사용자 정보가 없습니다."),
    ALREADY_EXISTING_USER(HttpStatus.CONFLICT, "E301", "이미 존재하는 사용자 입니다."),

    /**
     * E10000
     * http status 500 error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E10000", "정의되지 않은 오류입니다. 관리자에게 문의하세요."); // 500

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
