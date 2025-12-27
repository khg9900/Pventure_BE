package com.example.pventure.domain.schedule.docs;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = ScheduleSwaggerDocs.GET_ONE_SUMMARY,
        description = ScheduleSwaggerDocs.GET_ONE_DESCRIPTION
)
@ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true)
@ApiResponse(responseCode = "404", description = "여행 또는 사용자 없음")
public @interface GetScheduleDocs {}
