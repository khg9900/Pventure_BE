package com.example.pventure.domain.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeSlot {
    EARLY_MORNING("새벽", "새벽 시간대"),
    MORNING("아침", "오전 이른 시간대"),
    AFTER_MORNING("오전", "오전 늦은 시간대"),
    LUNCH("점심", "점심 시간대"),
    AFTER_LUNCH("오후", "오후 시간대"),
    EVENING("저녁", "저녁 시간대"),
    AFTER_EVENING("밤", "밤 시간대");

    private final String name;
    private final String description;
}