package com.example.pventure.domain.folder.docs;

public final class FolderSwaggerDocs {

    private FolderSwaggerDocs() {}

    // CREATE
    public static final String CREATE_SUMMARY = "폴더 생성";
    public static final String CREATE_DESCRIPTION = "사용자 ID와 폴더 정보를 전달하여 새로운 폴더를 생성합니다.";

    // GET ALL
    public static final String GET_ALL_SUMMARY = "폴더 목록 조회";
    public static final String GET_ALL_DESCRIPTION = "사용자 ID로 해당 사용자의 모든 폴더 목록을 조회합니다.";

    // GET DETAIL
    public static final String DETAIL_SUMMARY = "폴더 상세 조회";
    public static final String DETAIL_DESCRIPTION = "폴더 ID와 사용자 ID로 특정 폴더의 상세 정보를 조회합니다.";

    // UPDATE
    public static final String UPDATE_SUMMARY = "폴더 수정";
    public static final String UPDATE_DESCRIPTION = "폴더 ID와 사용자 ID를 통해 폴더 정보를 수정합니다.";

    // DELETE
    public static final String DELETE_SUMMARY = "폴더 삭제";
    public static final String DELETE_DESCRIPTION = "폴더 ID와 사용자 ID를 통해 특정 폴더를 삭제합니다.";
}
