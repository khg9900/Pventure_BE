package com.example.pventure.domain.schedule.controller;

import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.pventure.domain.schedule.service.ScheduleService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "일정 생성", description = "특정 여행(trip)에 일정을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "일정 생성 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "여행 또는 사용자 없음")
    })
    @PostMapping("/schedules")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> createSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "일정 생성 요청 DTO",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "day": 1,
                              "timeSlot": "MORNING",
                              "sequence": 1,
                              "isCompleted": true
                              "memo": "사진 많이 찍기"
                            }
                            """)))
            @Valid @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return CustomResponseHelper.created(
                scheduleService.createSchedule(tripId, userId, scheduleRequestDto)
        );
    }

    @Operation(summary = "특정 날짜의 일정 목록 조회", description = "지정된 여행의 특정 일(day)에 대한 모든 일정을 조회합니다.")
    @GetMapping("/days/{day}/schedules")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> getSchedules(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @Parameter(description = "조회할 일차 (day)", example = "1") @PathVariable Integer day
    ) {
        return CustomResponseHelper.ok(
                scheduleService.getSchedules(tripId, userId, day)
        );
    }

    @Operation(summary = "단일 일정 조회", description = "일정 ID로 특정 일정을 조회합니다.")
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

    @Operation(summary = "일정 수정", description = "기존 일정을 수정합니다.")
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> updateSchedule(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일정 ID", example = "5") @PathVariable Long scheduleId,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정 요청 DTO",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "memo": "사진 많이 찍기",
                              "isCompleted": true
                            }
                            """)))
            @Valid @RequestBody ScheduleUpdateDto scheduleUpdateDto
    ) {
        return CustomResponseHelper.ok(
                scheduleService.updateSchedule(tripId, scheduleId, userId, scheduleUpdateDto)
        );
    }

    @Operation(summary = "일정 순서 재정렬", description = "같은 날짜 내에서 일정 순서를 변경합니다.")
    @PutMapping("/days/{day}/schedules/reorder")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> reorderSchedules(
            @Parameter(description = "여행 ID", example = "1") @PathVariable Long tripId,
            @Parameter(description = "일차 (day)", example = "1") @PathVariable Integer day,
            @Parameter(description = "사용자 ID", example = "1") @RequestParam Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "재정렬 요청 DTO",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "oldSeq": 3,
                              "newSeq": 1,
                              "timeSlot": "MORNING"
                            }
                            """)))
            @Valid @RequestBody ScheduleReorderRequestDto scheduleReorderRequest
    ) {
        return CustomResponseHelper.ok(
                scheduleService.reorderSchedules(tripId, userId, day, scheduleReorderRequest)
        );
    }

    @Operation(summary = "일정 삭제", description = "지정된 일정(scheduleId)을 삭제합니다.")
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
