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
        summary = FolderSwaggerDocs.UPDATE_SUMMARY,
        description = FolderSwaggerDocs.UPDATE_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "수정 성공", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음")
})
public @interface UpdateFolderDocs {}
