package com.example.pventure.global.common;


import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import org.springframework.util.StringUtils;

public class ValidationUtils {

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static void requireNonEmpty(String str) {
        if (!StringUtils.hasText(str)) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
    }
}