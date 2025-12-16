package com.example.pventure.domain.trip.dto.request;

import com.example.pventure.domain.trip.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "여행 수정 요청 DTO")
public class TripUpdateDto implements FolderAttachable {
    @Schema(description = "여행 제목", example = "부산 여행")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "여행 썸네일 이미지 URL", example = "https://example.com/thumbnail.png")
    @URL(message = "썸네일 URL 형식이 올바르지 않습니다.")
    private String thumbnail;

    @Schema(description = "여행 목적지", example = "부산 해운대")
    private String destination;

    @Schema(description = "총 여행 일수", example = "5")
    private Integer totalDuration;

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

    public void applyTo(Trip trip) {
        if (title != null) {trip.updateTitle(title);}
        if (destination != null) {trip.updateDestination(destination);}
        if (thumbnail != null) {trip.updateThumbnail(thumbnail);}
        trip.updateDates(startDate, endDate);
        if (totalDuration != null && totalDuration > 0) {trip.updateTotalDuration(totalDuration);}
    }

}
