package com.example.pventure.domain.tripFolder.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TripFolderSwaggerDocs {

    // --- Add Trip to Folder ---
    public static final String ADD_TRIP_SUMMARY = "폴더에 여행 추가";
    public static final String ADD_TRIP_DESCRIPTION =
            "폴더 ID와 여행 ID를 전달하여 해당 폴더에 여행을 추가합니다.";

    // --- Get Trips in Folder ---
    public static final String GET_TRIPS_SUMMARY = "폴더 내 여행 조회";
    public static final String GET_TRIPS_DESCRIPTION =
            "폴더 ID로 해당 폴더에 속한 모든 여행을 조회합니다.";

    // --- Delete Trip from Folder ---
    public static final String DELETE_TRIP_SUMMARY = "폴더에서 여행 삭제";
    public static final String DELETE_TRIP_DESCRIPTION =
            "폴더 ID와 여행 ID를 전달하여 해당 폴더에서 여행을 삭제합니다.";
}
