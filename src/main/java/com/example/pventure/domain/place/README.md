# Place 도메인

장소 등록, 조회, 링크 제공을 담당하는 도메인입니다.

## 폴더 구조

place/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Place 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 장소 등록 / 수정 / 삭제
- 장소 상세 조회

## 활용 예시

// 장소 등록
PlaceResponse place = placeService.createPlace(createPlaceRequestDto);

// 장소 수정
PlaceResponse updatedPlace = placeService.updatePlace(placeId, updatePlaceRequestDto);

// 장소 삭제
placeService.deletePlace(placeId);
