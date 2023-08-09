# 스프링부트
## [0801](https://github.com/nxx5xxx/springBoot/blob/master/0801.md)
    JPA : Java Persistence API
    Entity
    database 초기화 DDL AUTO option
    Enum
    Query Method
    Junit테스트
    @Query 어노테이션 - JPQL
    Native Query
    QueryDSL

## [0802](https://github.com/nxx5xxx/springBoot/blob/master/0802.md)
    Validation
    Security
    PasswordEncoder
    DB에 insert
    회원정보 중복체크
    다 Enum사용

## [0804](https://github.com/nxx5xxx/springBoot/blob/master/0804.md)
    시큐리티 로그인 로그아웃
    리터럴데이터 영역설명
    람다짧게 더블콜론
    타임리프시큐리티
    연관관계맵핑

## [0807](https://github.com/nxx5xxx/springBoot/blob/master/0807.md)    
    외래키(foreign key)로 양방향 조회를 할 수 있음
    Controller > Service > Repository > Entity(DB)
    연관 관계 매핑 종류 - N : 1 / 1 : N 양방향 Mapping
    <Orders Entity>
    영속성 전이와 고아객체(orphan object)
     N : M Mapping  <-- 실무에서는 사용하지 않음
     @EntityListener :
     JPA에서 제공하는 7가지 Event
     @MappedSuperclass
     modelmapper library
     application.properties 파일에 파일 업로드 관련 설정하기
     < uploadFile() 메소드>
     fos.write(fileData);

## [0808](https://github.com/nxx5xxx/springBoot/blob/master/0808.md)  
    상품조회
    상품수정
    페이징처리

## [0809](https://github.com/nxx5xxx/springBoot/blob/master/0809.md)  
    질문 답변 보드프로젝트 따로만듬
    ResponseBody
    타임리프
    String.format