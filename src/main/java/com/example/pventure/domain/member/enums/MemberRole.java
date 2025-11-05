package com.example.pventure.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    VIEWER("보기 전용", "읽기 전용 권한"),
    EDITOR("편집자", "수정 및 편집 권한"),
    OWNER("관리자", "모든 권한을 가진 관리자");

    private final String name;

    private final String description;

}
