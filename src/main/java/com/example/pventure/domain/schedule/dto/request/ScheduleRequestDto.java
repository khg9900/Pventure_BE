package com.example.pventure.domain.schedule.dto.request;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.trip.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일정 생성 요청 DTO")
public class ScheduleRequestDto {

    @NotNull(message = "일차(day)는 필수 값입니다.")
    @Schema(description = "여행의 일차(day)", example = "1")
    private Integer day;

    @Schema(description = "정렬 순서", example = "1")
    private Integer sequence;

    @Schema(description = "일정 완료 여부", example = "false")
    private Boolean isCompleted;

    @Schema(description = "일정 메모", example = "사진 많이 찍기")
    private String memo;

    @NotNull(message = "시간대(timeSlot)는 필수 값입니다.")
    @Schema(description = "시간대", example = "MORNING")
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
