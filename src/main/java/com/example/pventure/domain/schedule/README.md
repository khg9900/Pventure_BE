# Schedule 도메인

여행 세부 일정 관리 기능을 담당하는 도메인입니다.

## 폴더 구조

schedule/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Schedule 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO


## 주요 기능

- 일정 생성 / 수정 / 삭제
- 일정 상세 조회
- 일정 순서 및 시간대 관리

## 활용 예시

```java
// 일정 생성
ScheduleResponse schedule = scheduleService.createSchedule(createScheduleRequestDto);

// 일정 수정
ScheduleResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, updateScheduleRequestDto);

// 일정 삭제
scheduleService.deleteSchedule(scheduleId);

// 일정 상세 조회
ScheduleResponse detail = scheduleService.getSchedule(scheduleId);
```
