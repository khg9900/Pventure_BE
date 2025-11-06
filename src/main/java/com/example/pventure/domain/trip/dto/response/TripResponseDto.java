package com.example.pventure.domain.trip.dto.response;

import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
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
public class TripResponseDto {

    private Long id;

    private String title;

    private String destination;

    private String thumbnail;

    private TripStatus tripStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalDuration;

    private Long memberCount;

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
