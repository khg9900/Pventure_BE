package com.example.pventure.domain.trip.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripStatus {
    COMPLETED("완료", "여행이 완료된 상태"),
    PLANNED("예정", "여행이 계획된 상태"),
    ONGOING("여행중", "여행이 진행 중인 상태");

    private final String name;
    private final String description;
}