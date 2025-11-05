package com.example.pventure.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    ACCEPTED("수락", "회원이 수락한 상태"),
    PENDING("대기", "회원 승인을 기다리는 상태"),
    REJECTED("거절", "회원이 거절한 상태"),
    CANCELED("취소", "회원이 취소한 상태"),
    EXPIRED("만료", "회원의 유효 기간이 만료된 상태");

    private final String name;
    private final String description;
}
