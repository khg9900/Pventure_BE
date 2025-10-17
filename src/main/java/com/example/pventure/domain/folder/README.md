# Folder 도메인

사용자별 폴더 생성, 수정, 삭제 및 앨범/사진/여행과의 구조적 연계를 담당하는 도메인입니다.  
기본 폴더(`isDefault = true`) 관리와 사용자 정의 폴더 기능을 제공합니다.

## 폴더 구조

folder/
├── controller/ # API 엔드포인트 정의
├── service/ # 비즈니스 로직
├── repository/ # DB 접근
├── entity/ # Folder 관련 엔티티
└── dto/
    ├── request/ # API 요청 DTO
    └── response/ # API 응답 DTO

## 주요 기능

- 폴더 생성 / 수정 / 삭제
- 기본 폴더(`default folder`) 관리
- 여행, 앨범, 사진 등과의 연동
- 사용자별 폴더 트리 관리  

// 폴더 생성
FolderResponse folder = folderService.createFolder(createFolderRequestDto, userId);

// 폴더 수정
FolderResponse updated = folderService.updateFolder(folderId, updateFolderRequestDto);

// 폴더 삭제
folderService.deleteFolder(folderId);

// 기본 폴더 조회
FolderResponse defaultFolder = folderService.getDefaultFolder(userId);