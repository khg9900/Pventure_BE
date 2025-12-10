package com.example.pventure.domain.schedule.docs;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = ScheduleSwaggerDocs.GET_ONE_SUMMARY,
        description = ScheduleSwaggerDocs.GET_ONE_DESCRIPTION
)
public @interface GetScheduleDocs {}
