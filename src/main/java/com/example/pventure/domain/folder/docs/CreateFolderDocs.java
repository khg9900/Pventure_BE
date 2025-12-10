package com.example.pventure.domain.folder.docs;

import com.example.pventure.domain.folder.docs.FolderSwaggerDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = FolderSwaggerDocs.CREATE_SUMMARY,
        description = FolderSwaggerDocs.CREATE_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "생성 성공", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "409", description = "기본 폴더 중복")
})
public @interface CreateFolderDocs {}
