package com.example.pventure.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider {
    KAKAO("카카오", "카카오 소셜 로그인"),
    NAVER("네이버", "네이버 소셜 로그인"),
    GOOGLE("구글", "구글 소셜 로그인");

    private final String name;
    private final String description;
}