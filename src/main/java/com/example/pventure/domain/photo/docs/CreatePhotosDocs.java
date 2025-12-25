package com.example.pventure.domain.photo.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = PhotoSwaggerDocs.CREATE_PHOTOS_SUMMARY,
    description = PhotoSwaggerDocs.CREATE_PHOTOS_DESCRIPTION
)
@ApiResponse(responseCode = "201", description = "사진 생성 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "400", description = "잘못된 요청")
@ApiResponse(responseCode = "403", description = "편집 권한 없음")
@ApiResponse(responseCode = "404", description = "사용자 또는 여행 없음")
public @interface CreatePhotosDocs {}