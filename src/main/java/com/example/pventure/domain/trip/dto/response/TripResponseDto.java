package com.example.pventure.domain.trip.dto.response;

import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "여행 상세 및 목록 응답 DTO")
public class TripResponseDto {

    @Schema(description = "여행 ID", example = "1")
    private Long id;

    @Schema(description = "여행 제목", example = "부산 여행")
    private String title;

    @Schema(description = "여행 목적지", example = "부산")
    private String destination;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.png")
    private String thumbnail;

    @Schema(description = "여행 상태", implementation = TripStatus.class)
    private TripStatus tripStatus;

    @Schema(description = "여행 시작일", example = "2025-05-01")
    private LocalDate startDate;

    @Schema(description = "여행 종료일", example = "2025-05-05")
    private LocalDate endDate;

    @Schema(description = "총 여행 기간(일수)", example = "5")
    private Integer totalDuration;

    @Schema(description = "여행 멤버 수", example = "3")
    private Long memberCount;

    @Schema(description = "여행 멤버 요약 정보 목록")
    private List<MemberSummaryDto> members;

    public static TripResponseDto from(Trip trip, List<MemberSummaryDto> members, Long memberCount) {

        return TripResponseDto.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .destination(trip.getDestination())
                .thumbnail(trip.getThumbnail())
                .tripStatus(trip.getStatus())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .totalDuration(trip.getTotalDuration())
                .memberCount(memberCount)
                .members(members)
                .build();
    }

}
