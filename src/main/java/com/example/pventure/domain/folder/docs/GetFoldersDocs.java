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
        summary = FolderSwaggerDocs.GET_ALL_SUMMARY,
        description = FolderSwaggerDocs.GET_ALL_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "사용자 없음")
})
public @interface GetFoldersDocs {}
