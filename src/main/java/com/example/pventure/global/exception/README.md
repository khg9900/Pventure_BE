# Exception 모듈

프로젝트 전역에서 사용할 사용자 정의 예외와 예외 처리 로직을 관리합니다.

## 주요 기능

- CustomException 정의
- GlobalExceptionHandler
- HTTP 상태 코드 매핑
- 예외 로그 기록

## 활용 예시

```java
if (user == null) {
    throw new ResourceNotFoundException("사용자를 찾을 수 없습니다.");
}