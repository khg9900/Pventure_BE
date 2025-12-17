package com.example.pventure.domain.schedule.docs;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = ScheduleSwaggerDocs.GET_BY_DAY_SUMMARY,
        description = ScheduleSwaggerDocs.GET_BY_DAY_DESCRIPTION
)
public @interface GetSchedulesDocs {}
