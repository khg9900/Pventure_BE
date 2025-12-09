package com.example.pventure.domain.trip.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripSwaggerDocs {

    // --- Create ---
    public static final String CREATE_TRIP_SUMMARY = "새 여행 생성";
    public static final String CREATE_TRIP_DESCRIPTION =
            "사용자 ID와 여행 정보를 입력하여 새로운 여행을 생성합니다.\n"
                    + "생성된 여행 정보는 즉시 반환되며, 필요 시 멤버 정보도 함께 조회할 수 있습니다.";

    // --- Get Trips ---
    public static final String GET_TRIPS_SUMMARY = "여행 목록 조회";
    public static final String GET_TRIPS_DESCRIPTION =
            "사용자 ID와 검색 조건을 기반으로 여행 목록을 조회합니다.\n"
                    + "여행 상태, 기간, 검색어 등 다양한 조건으로 필터링할 수 있으며, "
                    + "필요 시 멤버 정보도 함께 조회할 수 있습니다.";

    // --- Get Trip ---
    public static final String GET_TRIP_SUMMARY = "여행 상세 조회";
    public static final String GET_TRIP_DESCRIPTION =
            "사용자 ID와 여행 ID를 이용해 특정 여행의 상세 정보를 조회합니다.\n"
                    + "필요 시 해당 여행의 멤버 정보도 함께 확인할 수 있습니다.";

    // --- Update Trip ---
    public static final String UPDATE_TRIP_SUMMARY = "여행 정보 수정";
    public static final String UPDATE_TRIP_DESCRIPTION =
            "사용자 ID와 여행 ID를 이용해 등록된 여행 정보를 수정합니다.\n"
                    + "제목, 목적지, 날짜 등 수정이 가능합니다.";

    // --- Delete Trip ---
    public static final String DELETE_TRIP_SUMMARY = "여행 삭제";
    public static final String DELETE_TRIP_DESCRIPTION =
            "사용자 ID와 여행 ID를 입력하여 특정 여행을 삭제합니다.\n"
                    + "성공적으로 삭제되면 204 응답을 반환합니다.";
}

