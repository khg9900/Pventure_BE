package com.example.pventure.domain.tripFolder.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = TripFolderSwaggerDocs.DELETE_TRIP_SUMMARY,
        description = TripFolderSwaggerDocs.DELETE_TRIP_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "폴더, 여행 또는 사용자 없음")
})
public @interface DeleteTripInFolderDocs {}
