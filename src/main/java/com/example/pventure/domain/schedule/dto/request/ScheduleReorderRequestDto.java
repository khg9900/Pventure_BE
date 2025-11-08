package com.example.pventure.domain.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReorderRequestDto {

    Long scheduleId;
    Integer newSeq;
    Integer oldSeq;

}
