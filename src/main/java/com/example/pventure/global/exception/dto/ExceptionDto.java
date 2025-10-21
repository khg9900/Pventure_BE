package com.example.pventure.global.exception.dto;

import com.example.pventure.global.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class ExceptionDto {
    @NotNull
    private final String code;

    @NotNull
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}