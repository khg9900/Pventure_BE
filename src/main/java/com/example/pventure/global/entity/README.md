# Entity 모듈

프로젝트 전역에서 사용되는 공통 엔티티를 관리합니다.

## 주요 기능

- BaseEntity (id, createdAt, updatedAt)
- 공통 코드 엔티티
- 상속/재사용 가능 구조

## 활용 예시

```java
@Entity
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}