package com.example.pventure.domain.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일정 수정 요청 DTO")
public class ScheduleUpdateDto {

    @Schema(description = "수정된 메모", example = "사진 많이 찍기")
    private String memo;

    @Schema(description = "완료 여부 변경", example = "true")
    private Boolean isCompleted;
}
