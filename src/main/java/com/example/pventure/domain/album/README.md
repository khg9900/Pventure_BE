# Album 도메인

여행별 사진 앨범 관리 기능을 담당하는 도메인입니다.  
사진 업로드, 조회, 정렬, 공유 등 앨범 단위의 기능을 제공합니다.

## 폴더 구조

album/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Album 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 앨범 생성 / 수정 / 삭제
- 앨범별 사진 목록 조회
- 앨범 정렬 / 필터링
- 앨범 공유 기능

## 활용 예시

```java
// 앨범 생성
AlbumResponse album = albumService.createAlbum(createAlbumRequestDto);

// 앨범 수정
AlbumResponse updatedAlbum = albumService.updateAlbum(albumId, updateAlbumRequestDto);

// 앨범 삭제
albumService.deleteAlbum(albumId);

// 앨범 조회
AlbumResponse detail = albumService.getAlbum(albumId);
```
