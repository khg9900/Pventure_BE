package com.example.pventure.domain.schedule.dto.request;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDto {

    @NotNull(message = "일차(day)는 필수 값입니다.")
    private Integer day;

    private Integer sequence;

    private Boolean isCompleted;

    private String memo;

    @NotNull(message = "시간대(timeSlot)는 필수 값입니다.")
    private TimeSlot timeSlot;

    public Schedule toEntity(Trip trip,Integer finalSeq) {
        return Schedule.builder()
                .day(day)
                .sequence(finalSeq)
                .isCompleted(isCompleted != null && isCompleted)
                .memo(memo)
                .timeSlot(timeSlot)
                .trip(trip)
                .build();
    }
}
