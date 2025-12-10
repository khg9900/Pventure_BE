package com.example.pventure.domain.schedule.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleSwaggerDocs {

    // --- Create ---
    public static final String CREATE_SUMMARY = "일정 생성";
    public static final String CREATE_DESCRIPTION =
            "특정 여행(trip)에 일정을 추가합니다.\n"
                    + "day, timeSlot, sequence, memo 등을 입력하여 새로운 일정을 생성합니다.";

    // --- Get By Day ---
    public static final String GET_BY_DAY_SUMMARY = "특정 날짜의 일정 목록 조회";
    public static final String GET_BY_DAY_DESCRIPTION =
            "지정된 여행의 특정 일(day)에 대한 모든 일정을 조회합니다.";

    // --- Get Single ---
    public static final String GET_ONE_SUMMARY = "단일 일정 조회";
    public static final String GET_ONE_DESCRIPTION =
            "일정 ID로 특정 일정을 조회합니다.";

    // --- Update ---
    public static final String UPDATE_SUMMARY = "일정 수정";
    public static final String UPDATE_DESCRIPTION =
            "기존 일정의 메모 또는 완료 여부 등을 수정합니다.";

    // --- Reorder ---
    public static final String REORDER_SUMMARY = "일정 순서 재정렬";
    public static final String REORDER_DESCRIPTION =
            "같은 날짜 내에서 일정의 순서를 변경합니다.";

    // --- Delete ---
    public static final String DELETE_SUMMARY = "일정 삭제";
    public static final String DELETE_DESCRIPTION =
            "지정된 일정(scheduleId)을 삭제합니다.";
}
