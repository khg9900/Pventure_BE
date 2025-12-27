package com.example.pventure.domain.album.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = AlbumSwaggerDocs.CREATE_ALBUM_SUMMARY,
    description = AlbumSwaggerDocs.CREATE_ALBUM_DESCRIPTION
)
@ApiResponse(responseCode = "201", description = "생성 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "400", description = "잘못된 요청")
@ApiResponse(responseCode = "403", description = "편집 권한 없음")
@ApiResponse(responseCode = "404", description = "사용자 또는 여행 없음")
public @interface CreateAlbumDocs {}