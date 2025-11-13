package com.example.pventure.domain.schedule.dto.response;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.trip.entity.Trip;
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
    private TimeSlot timeSlot;

    public static ScheduleResponseDto from(Schedule schedule) {
        LocalDate date = null;
        Trip trip = schedule.getTrip();
        Integer day = schedule.getDay();

        if (trip != null && trip.getStartDate() != null && day != null) {
            date = trip.getStartDate().plusDays(day - 1);
        }

        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getId())
                .day(day)
                .sequence(schedule.getSequence())
                .isCompleted(schedule.isCompleted())
                .date(date)
                .memo(schedule.getMemo())
                .timeSlot(schedule.getTimeSlot())
                .build();
    }
}
