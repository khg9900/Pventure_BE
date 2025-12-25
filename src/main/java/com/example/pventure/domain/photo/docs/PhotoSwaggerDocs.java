package com.example.pventure.domain.photo.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoSwaggerDocs {

    // --- Generate Upload Presigned Urls ---
    public static final String GENERATE_UPLOAD_URLS_SUMMARY = "사진 업로드용 Presigned URL 생성";
    public static final String GENERATE_UPLOAD_URLS_DESCRIPTION = "사진을 업로드하기 위한 S3 Presigned-URL 목록을 생성합니다.";

    // --- Create ---
    public static final String CREATE_PHOTOS_SUMMARY = "사진 저장";
    public static final String CREATE_PHOTOS_DESCRIPTION =
        "클라이언트가 S3 업로드를 완료한 뒤, 사용자 ID, 여행 ID, 앨범 ID, 사진 정보(s3Key, 파일 정보 등)를 저장합니다.";
    
    // --- Get Photos By Album---
    public static final String GET_PHOTOS_BY_ALBUM_SUMMARY = "앨범별 사진 목록 조회";
    public static final String GET_PHOTOS_BY_ALBUM_DESCRIPTION = "사용자 ID, 여행 ID, 앨범 ID로 특정 앨범에 포함된 사진 목록을 조회합니다. "
        + "목록 조회 시 사진 ID와 다운로드용 Presigned URL만 반환합니다.";

    // --- Get Unassigned Photos ---
    public static final String GET_UNASSIGNED_PHOTOS_SUMMARY = "미분류 사진 목록 조회";
    public static final String GET_UNASSIGNED_PHOTOS_DESCRIPTION =
        "사용자 ID, 여행 ID로 앨범이 지정되지 않은(Album = null) 사진 목록을 조회합니다. "
            + "목록 조회 시 사진 ID와 다운로드용 Presigned URL만 반환합니다.";

    // --- Get Photo ---
    public static final String GET_PHOTO_SUMMARY = "사진 상세 조회";
    public static final String GET_PHOTO_DESCRIPTION = "사용자 ID, 여행 ID, 사진 ID로 특정 사진의 상세 정보를 조회합니다.";
    
    // --- Move Photos ---
    public static final String MOVE_PHOTOS_SUMMARY = "사진 일괄 이동";
    public static final String MOVE_PHOTOS_DESCRIPTION = "선택한 사진을 특정 앨범으로 일괄 이동합니다. ";

    // --- Delete Photos ---
    public static final String DELETE_PHOTO_SUMMARY = "사진 일괄 삭제";
    public static final String DELETE_PHOTO_DESCRIPTION = "선택한 사진을 DB와 S3에서 일괄 삭제합니다.";
}
