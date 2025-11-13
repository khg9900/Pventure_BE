package com.example.pventure.domain.schedule.dto.request;

import com.example.pventure.domain.schedule.enums.TimeSlot;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReorderRequestDto {

    @NotNull(message = "스케줄 ID는 필수 값입니다.")
    private Long scheduleId;

    @NotNull(message = "새로운 순번(newSeq)은 필수 값입니다.")
    private Integer newSeq;

    @NotNull(message = "기존 순번(oldSeq)은 필수 값입니다.")
    private Integer oldSeq;

    @NotNull(message = "시간대(timeSlot)는 필수 값입니다.")
    private TimeSlot timeSlot;
}