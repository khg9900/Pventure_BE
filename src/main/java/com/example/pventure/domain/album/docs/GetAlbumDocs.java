package com.example.pventure.domain.album.docs;

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
    summary = AlbumSwaggerDocs.GET_ALBUM_SUMMARY,
    description = AlbumSwaggerDocs.GET_ALBUM_DESCRIPTION
)
@ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "403", description = "보기 권한 없음")
@ApiResponse(responseCode = "404", description = "사용자 또는 여행 또는 앨범 없음")
public @interface GetAlbumDocs {}