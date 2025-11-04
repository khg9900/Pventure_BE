package com.example.pventure.domain.schedule.dto.response;

import com.example.pventure.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleResponseDto {

    private Long scheduleId;
    private boolean isCompleted;
    private Integer day;
    private Integer sequence;
    private LocalDate date;
    private String memo;

    public static ScheduleResponseDto from(Schedule schedule) {
        LocalDate date = null;
        if (schedule.getTrip().getStartDate() != null) {
            date = schedule.getTrip().getStartDate().plusDays(schedule.getDay() - 1);
        }
        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getId())
                .day(schedule.getDay())
                .sequence(schedule.getSequence())
                .isCompleted(schedule.isCompleted())
                .date(date)
                .memo(schedule.getMemo())
                .build();
    }
}
