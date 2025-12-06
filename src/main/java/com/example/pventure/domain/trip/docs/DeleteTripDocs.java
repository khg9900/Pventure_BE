package com.example.pventure.domain.trip.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = TripSwaggerDocs.DELETE_TRIP_SUMMARY,
        description = TripSwaggerDocs.DELETE_TRIP_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "여행 없음")
})
public @interface DeleteTripDocs {}
