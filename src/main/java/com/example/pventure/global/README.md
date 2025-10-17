# Global 패키지

프로젝트 전역에서 공통적으로 사용되는 모듈들을 모아둔 패키지입니다.  
각 모듈의 역할과 활용 예시를 문서화하여 팀원들이 쉽게 이해하고 재사용할 수 있도록 합니다.

## 하위 패키지 설명

- **ai/** : AI 관련 공통 기능 및 모듈
    - 예: 텍스트 분석, 추천 알고리즘, 모델 호출 래퍼

- **crawling/** : 크롤링 관련 기능
    - 예: 외부 사이트 데이터 수집, HTML 파싱, 스케줄 기반 크롤링

- **common/** : 유틸리티 및 공통 로직
    - 예: 날짜/시간 처리, 문자열 처리

- **exception/** : 전역 예외 처리
    - 예: 사용자 정의 예외(CustomException), GlobalExceptionHandler

- **jwt/** : JWT 토큰 관련 모듈
    - 예: AccessToken/RefreshToken 생성, 토큰 검증 필터

- **response/** : 공통 응답 형태 정의
    - 예: ApiResponse, PageResponse 등

- **entity/** : 공통 엔티티 정의
    - 예: BaseEntity(id, createdAt, updatedAt), 공통 코드 엔티티

- **config/** : 전역 설정 모듈
    - 예: Spring Security 설정, Redis/Swagger/QueryDsl/WebConfig 설정, API 공통 설정
