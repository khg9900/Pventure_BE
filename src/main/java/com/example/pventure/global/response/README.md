# Response 모듈

공통 응답 형태를 정의합니다.  
ApiResponse, PageResponse 등 프로젝트 전반에서 일관된 응답 구조를 제공합니다.

## 활용 예시

```java
return ApiResponse.success("조회 성공", data);
return PageResponse.of(items, totalElements);