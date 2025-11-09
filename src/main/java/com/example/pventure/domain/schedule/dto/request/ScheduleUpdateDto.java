package com.example.pventure.domain.schedule.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleUpdateDto {

    @NotNull(message = "완료 여부(isCompleted)는 필수 값입니다.")
    private Boolean isCompleted;

}
