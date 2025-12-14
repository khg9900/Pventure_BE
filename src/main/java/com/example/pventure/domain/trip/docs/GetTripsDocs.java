package com.example.pventure.domain.trip.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = TripSwaggerDocs.GET_TRIPS_SUMMARY,
        description = TripSwaggerDocs.GET_TRIPS_DESCRIPTION
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
})
public @interface GetTripsDocs {}
