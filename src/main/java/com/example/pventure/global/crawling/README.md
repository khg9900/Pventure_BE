# Crawling 모듈

외부 사이트 데이터 수집 및 크롤링 관련 기능을 관리합니다.  
HTML 파싱, API 호출, 스케줄 기반 데이터 수집 등을 포함합니다.

## 주요 기능

- 웹 크롤링
- HTML/JSON 파싱
- 스케줄 기반 데이터 수집
- 크롤링 결과 DB 저장

## 활용 예시

```java
// 크롤링 서비스 호출 예시
@Autowired
private CrawlingService crawlingService;

List<Data> results = crawlingService.crawlSite("https://example.com");