package com.soon83.springdatajpa.error;

import com.soon83.springdatajpa.dto.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * JSON parse error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleBindException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_JSON_INPUT, ErrorResponse.FieldError.of());
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * binding error
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleBindException(BindException e) {
        log.error("handleBindException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BIND_ERROR, e.getBindingResult());
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * HttpMessageConverter에서 파라미터를 binding 못할 때
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * enum type 일치하지 않을 때
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        final ErrorResponse response = ErrorResponse.of(e);
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * 데이터 무결성 제약조건 위반
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("handleDataIntegrityViolationException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DATA_INTEGRITY_VIOLATION_ERROR);
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 때
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpClientErrorException.MethodNotAllowed.class})
    protected ResponseEntity<Res<ErrorResponse>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(Res.fail(response));
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않았을 때
     */
    /*@ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN);
        return new ResponseEntity(Res.fail(response), HttpStatus.FORBIDDEN);
    }*/

    /**
     * 비즈니스 로직 오류
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getErrorMessage());
        return ResponseEntity.badRequest().body(Res.fail(response));
    }

    /**
     * 런타임 오류
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Res<ErrorResponse>> handleException(Exception e) {
        log.error("handleRuntimeException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(Res.fail(response));
    }
}