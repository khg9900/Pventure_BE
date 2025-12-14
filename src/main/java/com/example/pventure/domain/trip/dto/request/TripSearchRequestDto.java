package com.example.pventure.domain.trip.dto.request;

import com.example.pventure.domain.trip.enums.TripDateFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "여행 목록 조회 시 사용되는 검색 조건 DTO")
public class TripSearchRequestDto {
    @Schema(description = "검색 시작일 (여행 시작일 기준)", example = "2025-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "검색 종료일 (여행 종료일 기준)", example = "2025-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "여행 날짜 필터", implementation = TripDateFilter.class)
    private TripDateFilter tripDateFilter = TripDateFilter.ALL;

}