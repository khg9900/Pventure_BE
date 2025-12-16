package com.example.pventure.domain.folder.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = FolderSwaggerDocs.DETAIL_SUMMARY,
        description = FolderSwaggerDocs.DETAIL_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음")
})
public @interface GetFolderDocs {}
