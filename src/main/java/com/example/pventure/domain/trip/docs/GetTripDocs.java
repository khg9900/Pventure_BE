package com.example.pventure.domain.trip.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = TripSwaggerDocs.GET_TRIP_SUMMARY,
        description = TripSwaggerDocs.GET_TRIP_DESCRIPTION
)
@ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "404", description = "여행 없음", content = @Content)
public @interface GetTripDocs {}
