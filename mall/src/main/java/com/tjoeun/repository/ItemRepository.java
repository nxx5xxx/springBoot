package com.tjoeun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tjoeun.entity.Item;


//public interface ItemRepository extends JpaRepository<Item,Long>{
//쿼리 dsl추가
public interface ItemRepository extends JpaRepository<Item,Long>,QuerydslPredicateExecutor<Item>,ItemCustomRepository{
	//find(Entity클래스 이름) By(멤버변수이름)
	//엔티티 클래스 이름은 제네릭으로 지정했기때문에 생략할수있다
	List<Item> findByItemNm(String ItemNm); // select * from item where itemNm
	//여기서 findByItemNm()메소드를 쿼리 메소드라고 한다
	
	//이름이나 상세설명으로 검색
	List<Item> findByItemNmOrItemDetail(String ItemNm, String itemDetail);
	
	// JPQL
	
	//sql과 비슷하지만 테이블이름이 아닌 java 엔티티이름 을 불러온다
	// 참조변수를 하나 써줘야한다 여기서는 i가 참조변수
	//:파라미터이름 은 파라미터로 받은값을 대입한다는 뜻
	// Param으로 설정한 이름을 :안에 넣어도되고 Param을 따로 설정안해주면 원래 멤버필드명
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

	// Native Query
	@Query(value="select * from item i where i.item_detail like "
			+ "%:itemDetail% order by i.price desc",nativeQuery=true)
	List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);
}
