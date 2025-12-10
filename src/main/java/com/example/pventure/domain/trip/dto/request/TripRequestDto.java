package com.example.pventure.domain.trip.dto.request;

import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "여행 생성 요청 DTO")
public class TripRequestDto implements FolderAttachable {
    @Schema(description = "여행 제목", example = "부산 여행")
    @NotBlank(message = "여행 제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "여행 썸네일 이미지 URL", example = "https://example.com/thumbnail.png")
    @URL(message = "썸네일 URL 형식이 올바르지 않습니다.")
    private String thumbnail;

    @Schema(description = "여행 목적지", example = "부산 해운대")
    @NotBlank(message = "여행 목적지는 필수 입력값입니다.")
    private String destination;

    @Schema(description = "총 여행 일수", example = "5")
    @NotNull(message = "여행 기간(totalDuration)은 필수 입력값입니다.")
    private Integer totalDuration;

    @Schema(description = "여행 상태", example = "PLANNED")
    private TripStatus tripStatus;

    @Schema(description = "여행 시작일", example = "2025-01-20")
    private LocalDate startDate;

    @Schema(description = "여행 종료일", example = "2025-01-24")
    private LocalDate endDate;

    @Schema(description = "소속될 폴더 ID", example = "10")
    private Long folderId;

    @AssertTrue(message = "종료일은 시작일 이후여야 합니다.")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }

    public Trip toEntity() {
        Integer duration= this.totalDuration;

        if (startDate != null && endDate != null) {
            long days = ChronoUnit.DAYS.between(startDate, endDate);
            duration = (int) days + 1;
        }

        return Trip.builder()
                .title(title)
                .thumbnail(thumbnail)
                .destination(destination)
                .totalDuration(duration != null ? duration : 0)
                .status(tripStatus != null ? tripStatus : TripStatus.PLANNED)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
