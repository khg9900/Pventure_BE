# Member 도메인

팀 멤버 관리 및 초대/탈퇴 기능을 담당하는 도메인입니다.

## 폴더 구조

member/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Member 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO


## 주요 기능

- 팀 멤버 초대 / 삭제
- 멤버 권한 설정
- 팀 내 멤버 목록 조회
- 멤버 상태 관리 (활성/비활성)

## 활용 예시

```java
// 팀 멤버 초대
memberService.addMember(teamId, addMemberRequestDto);

// 팀 멤버 제거
memberService.removeMember(teamId, memberId);

// 팀 멤버 목록 조회
List<MemberResponse> members = memberService.getTeamMembers(teamId);
