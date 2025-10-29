package com.example.pventure.global.response;

import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.dto.ExceptionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public record CustomResponse<T>(
        @JsonIgnore
        HttpStatus httpStatus,
        boolean success,
        @Nullable T data,
        @Nullable ExceptionDto error
) {

    public static <T> CustomResponse<T> ok(@Nullable final T data) {
        return new CustomResponse<>(HttpStatus.OK, true, data, null);
    }

    public static <T> CustomResponse<T> created(@Nullable final T data) {
        return new CustomResponse<>(HttpStatus.CREATED, true, data, null);
    }

    public static <T> CustomResponse<T> fail(final ApiException e) {
        return new CustomResponse<>(e.getErrorCode().getHttpStatus(), false, null, ExceptionDto.of(e.getErrorCode()));
    }
}