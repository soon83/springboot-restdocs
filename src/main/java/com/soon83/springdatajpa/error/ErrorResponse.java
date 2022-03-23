package com.soon83.springdatajpa.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private int status;
    private HttpStatus error;
    private String code;
    private String message;
    private List<FieldError> errors;

    private ErrorResponse(final ErrorCode error, final List<FieldError> errors) {
        this.status = error.getStatus().value();
        this.error = error.getStatus();
        this.code = error.getErrorCode();
        this.message = error.getMessage();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode error, final String errorMessage) {
        this.status = error.getStatus().value();
        this.error = error.getStatus();
        this.code = error.getErrorCode();
        this.message = errorMessage;
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code, new ArrayList<>());
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode code, String errorMessage) {
        return new ErrorResponse(code, errorMessage);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of() {
            return new ArrayList<>();
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(e -> FieldError.builder()
                            .field(e.getField())
                            .value(getRejectedValue(e))
                            .reason(e.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());
        }

        private static String getRejectedValue(org.springframework.validation.FieldError error) {
            return error.getRejectedValue() == null ? "" : error.getRejectedValue().toString();
        }
    }
}
