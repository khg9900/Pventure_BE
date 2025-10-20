# Auth 도메인

로그인, 로그아웃, JWT 토큰 재발급 등 인증 관련 기능을 담당하는 도메인입니다.

## 폴더 구조
auth/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능
- 로그인 / 로그아웃
- AccessToken / RefreshToken 발급 및 검증
- 토큰 재발급
- 인증 관련 예외 처리

## 활용 예시

```java
// 로그인 처리
AuthResponse response = authService.login(loginRequestDto);

// 토큰 재발급
TokenResponse newToken = authService.refreshToken(refreshTokenRequestDto);
```