# TripFolder 도메인

**여행(Trip)** 과 **폴더(Folder)** 간의 관계를 관리하는 도메인입니다.  
여행 데이터를 특정 폴더에 분류하거나, 폴더별 여행 목록을 조회하는 역할을 담당합니다.  
즉, 사용자 맞춤형 여행 정리 구조를 제공하기 위한 매핑 관리 모듈입니다.

---

## 폴더 구조

tripFolder/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Trip-Folder 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

---

## 주요 기능

- 여행-폴더 매핑 생성 및 삭제
- 폴더 내 여행 목록 조회
- 특정 여행이 속한 폴더 조회
- 사용자별 여행 폴더 매핑 정보 관리

---

## 활용 예시

```java
// 여행을 폴더에 연결
TripFolderResponse link = tripFolderService.linkTripToFolder(tripId, folderId);

// 폴더 내 여행 리스트 조회
List<TripResponse> trips = tripFolderService.getTripsByFolder(folderId);

// 특정 여행이 속한 폴더 조회
FolderResponse folder = tripFolderService.getFolderByTrip(tripId);

// 여행-폴더 매핑 삭제
tripFolderService.unlinkTripFromFolder(tripId, folderId);
```