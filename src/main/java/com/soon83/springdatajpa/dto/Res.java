package com.soon83.springdatajpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Res<T> implements Serializable {

    private boolean success;
    private String code;
    private String message;
    private T data;

    @Builder
    public Res(ResultCode resultCode, T data) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.name();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    // success
    public static Res success() {
        return Res.builder()
                .resultCode(ResultCode.SUCCESS)
                .build();
    }

    // success with result data
    public static <T> Res<T> success(T data) {
        return (Res<T>) Res.builder()
                .resultCode(ResultCode.SUCCESS)
                .data(data)
                .build();
    }

    // error
    public static Res fail() {
        return Res.builder()
                .resultCode(ResultCode.FAIL)
                .build();
    }

    // error with error data
    public static <T> Res<T> fail(T data) {
        return (Res<T>) Res.builder()
                .resultCode(ResultCode.FAIL)
                .data(data)
                .build();
    }
}
