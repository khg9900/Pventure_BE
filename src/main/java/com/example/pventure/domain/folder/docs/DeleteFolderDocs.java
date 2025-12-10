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
        summary = FolderSwaggerDocs.DELETE_SUMMARY,
        description = FolderSwaggerDocs.DELETE_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음")
})
public @interface DeleteFolderDocs {}
