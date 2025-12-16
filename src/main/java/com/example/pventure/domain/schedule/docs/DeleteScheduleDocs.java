package com.example.pventure.domain.schedule.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = ScheduleSwaggerDocs.DELETE_SUMMARY,
        description = ScheduleSwaggerDocs.DELETE_DESCRIPTION
)
@ApiResponse(responseCode = "204", description = "삭제 성공")
@ApiResponse(responseCode = "404", description = "일정 또는 사용자 없음")
public @interface DeleteScheduleDocs {}
