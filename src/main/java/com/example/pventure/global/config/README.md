# Config 모듈

프로젝트 전역 설정 모듈을 관리합니다.

## 주요 기능

- Spring Security 설정
- Redis 설정
- Swagger / QueryDSL / WebConfig 설정
- API 공통 설정

## 활용 예시

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Security 설정
}
```