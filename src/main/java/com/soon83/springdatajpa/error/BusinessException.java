package com.soon83.springdatajpa.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String errorMessage;
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String fieldName, String message) {
        super(String.format("%s %s", message, fieldName));
        this.errorMessage = String.format("%s %s", message, fieldName);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String fieldName) {
        super(String.format("%s %s", errorCode.getMessage(), fieldName));
        this.errorMessage = String.format("%s %s", errorCode.getMessage(), fieldName);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorMessage = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}
