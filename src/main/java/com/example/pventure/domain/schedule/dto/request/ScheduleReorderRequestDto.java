package com.example.pventure.domain.schedule.dto.request;

import com.example.pventure.domain.schedule.enums.TimeSlot;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일정 순서 재정렬 요청 DTO")
public class ScheduleReorderRequestDto {

    @NotNull(message = "스케줄 ID는 필수 값입니다.")
    @Schema(description = "일정 ID", example = "5")
    private Long scheduleId;

    @NotNull(message = "새로운 순번(newSeq)은 필수 값입니다.")
    @Schema(description = "새로운 순서", example = "3")
    private Integer newSeq;

    @NotNull(message = "기존 순번(oldSeq)은 필수 값입니다.")
    @Schema(description = "변경할 순서", example = "1")
    private Integer oldSeq;

    @NotNull(message = "시간대(timeSlot)는 필수 값입니다.")
    @Schema(description = "시간대", example = "MORNING")
    private TimeSlot timeSlot;
}