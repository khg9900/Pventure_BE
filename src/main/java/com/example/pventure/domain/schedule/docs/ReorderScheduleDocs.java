package com.example.pventure.domain.schedule.docs;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = ScheduleSwaggerDocs.REORDER_SUMMARY,
        description = ScheduleSwaggerDocs.REORDER_DESCRIPTION
)
public @interface ReorderScheduleDocs {}
