package com.example.pventure.domain.schedule.service;

import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(Long tripId, Long userId, ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> getSchedules(Long tripId, Long userId, Integer day);

    List<ScheduleResponseDto> reorderSchedules(Long tripId, Long userId, Integer day, ScheduleReorderRequestDto scheduleReorderRequestDto);

    ScheduleResponseDto getSchedule(Long tripId, Long scheduleId, Long userId);

    ScheduleResponseDto updateSchedule(Long tripId, Long scheduleId, Long userId, ScheduleUpdateDto scheduleUpdateDto);

    void deleteSchedule(Long tripId, Long scheduleId, Long userId);
}
