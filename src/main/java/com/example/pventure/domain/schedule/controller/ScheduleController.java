package com.example.pventure.domain.schedule.controller;

import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.pventure.domain.schedule.service.ScheduleService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips/{tripId}")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> createSchedule(
            @PathVariable Long tripId,
            @RequestParam Long userId,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return CustomResponseHelper.created(
                scheduleService.createSchedule(tripId, userId, scheduleRequestDto)
        );
    }

    @GetMapping("/days/{day}/schedules")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> getSchedules(
            @PathVariable Long tripId,
            @RequestParam Long userId,
            @PathVariable Integer day
    ) {
        return CustomResponseHelper.ok(
                scheduleService.getSchedules(tripId, userId, day)
        );
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> getSchedule(
            @PathVariable Long tripId,
            @PathVariable Long scheduleId,
            @RequestParam Long userId
    ) {
        return CustomResponseHelper.ok(
                scheduleService.getSchedule(tripId, scheduleId, userId)
        );
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<CustomResponse<ScheduleResponseDto>> updateSchedule(
            @PathVariable Long tripId,
            @PathVariable Long scheduleId,
            @RequestParam Long userId,
            @RequestBody ScheduleUpdateDto scheduleUpdateDto
    ) {
        return CustomResponseHelper.ok(
                scheduleService.updateSchedule(tripId, scheduleId, userId, scheduleUpdateDto)
        );
    }

    @PutMapping("/days/{day}/schedules/reorder")
    public ResponseEntity<CustomResponse<List<ScheduleResponseDto>>> reorderSchedules(
            @PathVariable Long tripId,
            @PathVariable Integer day,
            @RequestParam Long userId,
            @RequestBody ScheduleReorderRequestDto scheduleReorderRequest
    ) {

        return CustomResponseHelper.ok(scheduleService.reorderSchedules(tripId, userId, day, scheduleReorderRequest));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long tripId,
            @PathVariable Long scheduleId,
            @RequestParam Long userId
    ) {
        scheduleService.deleteSchedule(tripId, scheduleId, userId);
        return CustomResponseHelper.noContent();
    }
}
