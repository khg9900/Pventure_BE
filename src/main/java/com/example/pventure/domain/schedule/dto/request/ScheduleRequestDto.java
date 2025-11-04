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
    private Boolean isComplete;
    private String memo;

    public Schedule toEntity(Trip trip,Integer sequence) {
        return Schedule.builder()
                .day(this.day)
                .sequence(sequence)
                .memo(this.memo)
                .isCompleted(this.isComplete != null && this.isComplete)
                .trip(trip)
                .build();
    }
}
