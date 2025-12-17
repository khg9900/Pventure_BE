package com.example.pventure.domain.schedule.dto.response;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.trip.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일정 응답 DTO")
public class ScheduleResponseDto {

    @Schema(description = "일정 ID", example = "10")
    private Long id;

    @Schema(description = "여행 일차", example = "1")
    private Integer day;

    @Schema(description = "시간대 (MORNING / AFTERNOON / EVENING)", example = "MORNING")
    private TimeSlot timeSlot;

    @Schema(description = "정렬 순서", example = "1")
    private Integer sequence;

    @Schema(description = "완료 여부", example = "false")
    private Boolean isCompleted;

    @Schema(description = "해당 일정의 실제 날짜", example = "2025-01-03")
    private LocalDate date;

    @Schema(description = "메모", example = "사진 많이 찍기")
    private String memo;

    public static ScheduleResponseDto from(Schedule schedule) {

        Trip trip = schedule.getTrip();
        Integer day = schedule.getDay();
        LocalDate calculatedDate = null;

        if (trip != null && trip.getStartDate() != null && day != null) {
            calculatedDate = trip.getStartDate().plusDays(day - 1);
        }

        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .day(day)
                .timeSlot(schedule.getTimeSlot())
                .sequence(schedule.getSequence())
                .isCompleted(schedule.isCompleted())
                .date(calculatedDate)
                .memo(schedule.getMemo())
                .build();
    }
}
