package com.example.pventure.domain.place.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceCategory {
    TOURIST_ATTRACTION("관광", "관광 명소"),
    RESTAURANT("식당", "음식을 제공하는 장소"),
    CAFE("카페", "커피 및 음료를 제공하는 장소"),
    ACCOMMODATION("숙박", "숙소 및 호텔"),
    SHOPPING("쇼핑", "쇼핑할 수 있는 장소"),
    TRANSPORTATION("교통", "교통수단 및 교통 관련 장소"),
    EVENT("축제", "이벤트 및 축제"),
    OTHER("기타", "기타 장소");

    private final String name;
    private final String description;
}
