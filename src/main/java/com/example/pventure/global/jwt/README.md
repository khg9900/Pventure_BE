# JWT 모듈

JWT 인증/인가 관련 기능을 관리합니다.

## 주요 기능

- AccessToken / RefreshToken 생성
- JWT 검증 필터
- 토큰 만료 처리
- 사용자 권한 검증

## 활용 예시

```java
String token = jwtProvider.generateAccessToken(userId);
Claims claims = jwtProvider.validateToken(token);
```