package com.example.pventure.domain.schedule.dto.request;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.trip.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDto {

    private Integer day;
    private Integer sequence;
    private Boolean isCompleted;

    public Schedule toEntity(Trip trip,Integer finalSeq) {
        return Schedule.builder()
                .day(day)
                .sequence(finalSeq)
                .isCompleted(isCompleted != null && isCompleted)
                .trip(trip)
                .build();
    }
}
