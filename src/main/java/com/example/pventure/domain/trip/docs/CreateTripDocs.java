package com.example.pventure.domain.trip.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = TripSwaggerDocs.CREATE_TRIP_SUMMARY,
        description = TripSwaggerDocs.CREATE_TRIP_DESCRIPTION
)
@ApiResponse(responseCode = "201", description = "생성 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
public @interface CreateTripDocs {}
