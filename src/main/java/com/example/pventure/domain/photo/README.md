# Photo 도메인

앨범 내 사진 업로드, 조회, 삭제 등의 기능을 담당하는 도메인입니다.  
AWS S3와 연동되어 사진 파일을 저장하고, 메타데이터를 관리합니다.

## 폴더 구조

photo/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Photo 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 사진 업로드 / 삭제
- 사진 목록 조회
- 사진 상세 조회
- S3 이미지 URL 관리

## 활용 예시

```java
// 사진 업로드
PhotoResponse photo = photoService.uploadPhoto(uploadPhotoRequestDto, multipartFile);

// 사진 삭제
photoService.deletePhoto(photoId);

// 사진 목록 조회
List<PhotoResponse> photos = photoService.getPhotosByAlbum(albumId);

// 사진 상세 조회
PhotoResponse detail = photoService.getPhoto(photoId);
