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

## [0811](https://github.com/nxx5xxx/springBoot/blob/master/0811.md)  
    에러 창하나로 돌려쓰기 fragment
    페이징처리
    댓글개수
    스프링시큐리티
    시큐리티 기본설정
    로그인설정
    글쓴이설정
    Principal

## [0814](https://github.com/nxx5xxx/springBoot/blob/master/0814.md)
    글쓴이
    작성자
    수정, 수정일
    href는 @를 붙여준다
    HttpStatus.BAD_REQUEST
    Principal
    @PreAuthorize("isAuthenticated()")
    삭제
    추천 기능 추가
    앵커태그
    검색기능
    익명클래스
    QuestionService 의 search메소드
    JPQL

## [0816](https://github.com/nxx5xxx/springBoot/blob/master/0816.md)
    댓글 등록 , 수정, 삭제
    부트프로젝트 방향성 의논