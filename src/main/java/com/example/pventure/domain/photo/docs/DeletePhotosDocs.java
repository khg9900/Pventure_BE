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
    summary = PhotoSwaggerDocs.DELETE_PHOTO_SUMMARY,
    description = PhotoSwaggerDocs.DELETE_PHOTO_DESCRIPTION
)
@ApiResponse(responseCode = "204", description = "삭제 성공")
@ApiResponse(responseCode = "403", description = "편집 권한 없음")
@ApiResponse(responseCode = "404", description = "사용자 또는 여행 또는 사진 없음")
public @interface DeletePhotosDocs {}
