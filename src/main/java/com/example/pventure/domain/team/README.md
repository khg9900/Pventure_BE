# Team 도메인

팀 생성, 조회, 팀 정보 관리 기능을 담당하는 도메인입니다.

## 폴더 구조

team/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Team 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 팀 생성 / 수정 / 삭제
- 팀 목록 조회
- 팀 정보 업데이트
- 팀 리더 및 멤버 권한 관리

## 활용 예시

```java
// 팀 생성
TeamResponse team = teamService.createTeam(createTeamRequestDto);

// 사용자 팀 목록 조회
List<TeamResponse> teams = teamService.getUserTeams(userId);

// 팀 정보 수정
teamService.updateTeam(teamId, updateTeamRequestDto);
