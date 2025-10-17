# User 도메인

사용자 계정, 프로필, 회원 정보 관리 관련 기능을 담당하는 도메인입니다.

## 폴더 구조

user/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # User 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 회원가입 / 회원 탈퇴
- 프로필 조회 / 수정
- 사용자 권한 관리
- 비밀번호 변경, 인증 정보 관리

## 활용 예시

```java
// 사용자 프로필 조회
UserResponse profile = userService.getProfile(userId);

// 프로필 업데이트
userService.updateProfile(userId, updateProfileRequestDto);

// 회원 탈퇴
userService.deleteUser(userId);