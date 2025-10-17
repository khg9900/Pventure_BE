# Common 모듈

공통 유틸리티와 로직을 관리합니다.  
날짜/시간 처리, 문자열 처리, 공통 상수 등을 포함합니다.

## 주요 기능

- 날짜/시간 포맷팅
- 문자열 유틸리티
- 공통 상수 관리
- API 응답 표준화

## 활용 예시

```java
String formattedDate = DateUtils.format(LocalDateTime.now(), "yyyy-MM-dd");