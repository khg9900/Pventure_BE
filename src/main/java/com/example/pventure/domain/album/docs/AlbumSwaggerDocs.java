package com.example.pventure.domain.album.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumSwaggerDocs {

    // --- Create ---
    public static final String CREATE_ALBUM_SUMMARY = "앨범 생성";
    public static final String CREATE_ALBUM_DESCRIPTION = "사용자 ID, 여행 ID, 앨범 정보를 입력하여 새로운 여행 앨범을 생성합니다.";

    // --- Get Albums ---
    public static final String GET_ALBUMS_SUMMARY = "앨범 목록 조회";
    public static final String GET_ALBUMS_DESCRIPTION = "사용자 ID와 여행 ID로 특정 여행에 속한 모든 앨범 목록을 조회합니다.";

    // --- Get Album ---
    public static final String GET_ALBUM_SUMMARY = "앨범 상세 조회";
    public static final String GET_ALBUM_DESCRIPTION = "사용자 ID, 여행 ID, 앨범 ID로 특정 앨범의 상세 정보를 조회합니다.";

    // --- Update Album ---
    public static final String UPDATE_ALBUM_SUMMARY = "앨범 정보 수정";
    public static final String UPDATE_ALBUM_DESCRIPTION = "사용자 ID, 여행 ID, 앨범 ID를 통해 앨범 정보를 수정합니다.";

    // --- Delete Album ---
    public static final String DELETE_ALBUM_SUMMARY = "앨범 삭제";
    public static final String DELETE_ALBUM_DESCRIPTION = "사용자 ID, 여행 ID, 앨범 ID를 통해 특정 앨범을 삭제합니다.";
}
