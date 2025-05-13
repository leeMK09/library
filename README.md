# 프로젝트 
- Library API 
  - 도서 관리 장부 디지털 시스템 전환 프로젝트 

# 기능 요구사항 
- 도서 검색 
  - 카테고리 별로 도서를 검색할 수 있다 
  - 지은이 와 제목으로 도서를 검색할 수 있다 
- 도서 등록 
  - 최소 하나 이상의 카테고리와 함께 신규 도서를 등록한다
- 도서 변경
  - 도서는 카테고리를 변경할 수 있다.
- 도서 대여
  - 도서는 대여할 수 있다
  - 도서는 훼손된 경우 대여할 수 없다 
  - 도서는 분실된 경우 대여할 수 없다
- 카테고리 검색
  - 모든 카테고리를 조회할 수 있다.

# ERD 

![erd.png](./docs/images/erd.png)

</br>

# 아키텍쳐 

- Controller
- Service (Application)
- Repository

각 레이어는 자신의 하위 레이어만 참조하여 사용하도록 정의 

레이어 흐름 
Controller -> Service -> Repository

</br>

## 아키텍쳐 컨벤션 

**Controller**
- API 요청에 대한 DTO 는 `Request` 접미사를 붙이는 형태
- API 응답에 대한 DTO 는 `Response` 접미사를 붙이는 형태

**Service**
- Service 는 비즈니스 로직이 담긴 형태의 서비스 구조
  - 추가로 각 Service 를 혼합한 형태의 `Application` 를 추가할 수 있다 (Facade 개념)
 - Command (Mutation) 과 Query 를 분리하여 작성한다
 - 요청에 대한 DTO 는 `Command` 접미사를 붙이는 형태
 - 응답에 대한 DTO 는 `Result` 접미사를 붙이는 형태

**Repository**
- Repository 는 영속성 레이어의 구조
- 영속성 방법은 다양하므로 변경되지 않을 Interface 와 그에 대한 구현체를 따로 정의해야함
  - ex. `BookJpaRepository` / `BookRepository` (Interface)
 - 변경되지 않을 Interface 는 Domain 영역에 존재하도록

</br>

## 도메인 과 엔티티 분리

- POJO 형태로 도메인을 먼저 개발한 후 ORM 형태의 엔티티를 추가
- 이유
  - 중요한 도메인은 다른 기술에 속하지 않도록 정의
  - ORM 에 대한 필드 혹은 속성은 도메인과 분리하도록 -> 도메인은 비즈니스 로직만 내부에 존재하도록

</br>

## 추후 개선 방향 

- 검색 시스템은 데이터가 많을 경우 성능 이슈가 발생
- 현재는 LIKE 및 '%검색어%' 형태이므로 인덱스 조회 최적화 되지 않음
1. 페이지네이션을 통해 조회하려는 데이터 자체를 줄이자
2. 검색 최적화 방안 기술 선택 
