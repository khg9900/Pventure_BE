# Trip 도메인

여행 정보 관리 기능을 담당하는 도메인입니다.

## 폴더 구조
trip/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Trip 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 여행(시작일/ 종료일 /여행 소개/ 여행 제목) 생성 / 수정 / 삭제
- 여행 상세 조회

## 활용 예시

```java
// 여행 생성
TripResponse trip = tripService.createTrip(createTripRequestDto);

// 여행 수정
TripResponse updatedTrip = tripService.updateTrip(tripId, updateTripRequestDto);

// 여행 삭제
tripService.deleteTrip(tripId);

// 여행 상세 조회
TripResponse detail = tripService.getTrip(tripId);
```