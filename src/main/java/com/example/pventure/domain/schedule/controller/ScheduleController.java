package com.example.pventure.domain.schedule.controller;

import com.example.pventure.domain.schedule.docs.*;
import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.pventure.domain.schedule.service.ScheduleService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "일정 관련 API")
@RestController
@RequestMapping("/trips/{tripId}")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // ------------------ 일정 생성 ------------------
    @CreateScheduleDocs
    @PostMapping("/schedules")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> createSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @Valid @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return CustomResponseHelper.created(
                scheduleService.createSchedule(tripId, userId, scheduleRequestDto)
        );
    }

    // ------------------ 일정 목록 조회 ------------------
    @GetSchedulesDocs
    @GetMapping("/days/{day}/schedules")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> getSchedules(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @Parameter(description = "조회할 일차", example = "1") @PathVariable Integer day
    ) {
        return CustomResponseHelper.ok(
                scheduleService.getSchedules(tripId, userId, day)
        );
    }

    // ------------------ 일정 단일 조회 ------------------
    @GetScheduleDocs
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> getSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일정 ID", example = "5") @PathVariable Long scheduleId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId
    ) {
        return CustomResponseHelper.ok(
                scheduleService.getSchedule(tripId, scheduleId, userId)
        );
    }

    // ------------------ 일정 수정 ------------------
    @UpdateScheduleDocs
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> updateSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일정 ID", example = "5") @PathVariable Long scheduleId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @Valid @RequestBody ScheduleUpdateDto scheduleUpdateDto
    ) {
        return CustomResponseHelper.ok(
                scheduleService.updateSchedule(tripId, scheduleId, userId, scheduleUpdateDto)
        );
    }

    // ------------------ 일정 순서 재정렬 ------------------
    @ReorderScheduleDocs
    @PutMapping("/days/{day}/schedules/reorder")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> reorderSchedules(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일차", example = "1") @PathVariable Integer day,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @Valid @RequestBody ScheduleReorderRequestDto scheduleReorderRequest
    ) {
        return CustomResponseHelper.ok(
                scheduleService.reorderSchedules(tripId, userId, day, scheduleReorderRequest)
        );
    }

    // ------------------ 일정 삭제 ------------------
    @DeleteScheduleDocs
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일정 ID", example = "5") @PathVariable Long scheduleId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId
    ) {
        scheduleService.deleteSchedule(tripId, scheduleId, userId);
        return CustomResponseHelper.noContent();
    }
}
