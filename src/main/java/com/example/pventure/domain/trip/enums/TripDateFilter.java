package com.example.pventure.domain.trip.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripDateFilter {
    ALL("전체", "모든 여행 조회"),
    SPECIFIED("날짜 지정", "시작일과 종료일이 지정된 여행 조회"),
    UNSPECIFIED("미지정", "시작일과 종료일이 지정되지 않은 여행 조회");

    private final String name;
    private final String description;
}