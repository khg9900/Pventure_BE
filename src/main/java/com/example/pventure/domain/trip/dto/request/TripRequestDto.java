package com.example.pventure.domain.trip.dto.request;

import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import jakarta.validation.constraints.*;
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
public class TripRequestDto {

    @NotBlank(message = "여행 제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.")
    private String title;

    @URL(message = "썸네일 URL 형식이 올바르지 않습니다.")
    private String thumbnail;

    @NotBlank(message = "여행 목적지는 필수 입력값입니다.")
    private String destination;

    private TripStatus tripStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    @AssertTrue(message = "종료일은 시작일 이후여야 합니다.")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }

    public Trip toEntity() {
        return Trip.builder()
                .title(title)
                .thumbnail(thumbnail)
                .destination(destination)
                .status(tripStatus != null ? tripStatus : TripStatus.PLANNED)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
