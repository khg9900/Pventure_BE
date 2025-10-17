# AI 모듈

AI 관련 공통 기능 및 모듈을 관리합니다.  
텍스트 분석, 추천 알고리즘, 머신러닝/딥러닝 모델 호출 등을 포함합니다.

## 주요 기능

- 추천 알고리즘
- 텍스트 분석 및 자연어 처리
- 모델 호출 래퍼 서비스
- 학습/예측 API 통합

## 활용 예시

```java
// AI 추천 서비스 호출 예시
@Autowired
private RecommendationService recommendationService;

List<Item> recommended = recommendationService.getRecommendations(userId);
```