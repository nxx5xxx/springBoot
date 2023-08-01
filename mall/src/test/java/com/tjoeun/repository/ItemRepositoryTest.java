package com.tjoeun.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tjoeun.constant.ItemSellStatus;
import com.tjoeun.entity.Item;
import com.tjoeun.entity.QItem;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
//@Transactional
//@Transactional 이것은 테스트를하고 커밋을 하지않고 다시 롤백한다
@Log4j2
class ItemRepositoryTest {
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private EntityManager entityManager;
	
	public void createItemList() {
		for(int i = 1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("상품"+i);
			item.setPrice(10000+i*100);
			item.setItemDetail("상품상세설명"+i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			//save는 db에 insert한다는 의미
			Item savedItem = itemRepository.save(item);
		}
	}
	
	//DisplayName은 무슨테스트인지 출력해주는 어노테이션
	@Test
	@DisplayName("상품명조회테스트")
	public void findByItemNmtest() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemNm("상품1");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("상품명, 상품상세설명 or 테스트")
	public void findByItemNmOrItemDetailTest() {
		this.createItemList();
		List<Item> itemList =itemRepository.findByItemNmOrItemDetail("상품1", "상품상세설명5");
		System.out.println("\n ----------------------ItemNm이나 ItemDetail에 해당하는 상품 가져오기시작-------------------");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		System.out.println("\n ----------------------ItemNm이나 ItemDetail에 해당하는 상품 가져오기종료-------------------");
	}

	
	@Test
	@DisplayName("JPQL 쿼리")
	public void findByItemDetailTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemDetail("6");
		System.out.println("\n ----------------------ItemDetail에 나온 단어 중 일부에 해당하는 상품 가져오기시작-------------------");
		for(Item item : itemList) {
			//System.out.println(item);
			log.info(item);
		}
		System.out.println("\n ----------------------ItemDetail에 나온 단어 중 일부에 해당하는 상품 가져오기종료-------------------");
	}
	
	//네이티브쿼리
	@Test
	@DisplayName("Native 쿼리")
	public void findByItemDetailNativeTest() {
		createItemList();
		
		List<Item> itemList = itemRepository.findByItemDetailNative("상세설명");
		System.out.println("\n ----------------------Native로 ItemDetail에 나온 단어 중 일부에 해당하는 상품 가져오기시작-------------------");
		for(Item item : itemList) {
			System.out.println(item);
			//log.info(item);
		}
		System.out.println("\n ----------------------Native로 ItemDetail에 나온 단어 중 일부에 해당하는 상품 가져오기종료-------------------");		
	}
	
	@Test
	@DisplayName("queryDSL 테스트")
	public void querydslTest() {
		createItemList();
		//entity query factory
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		//QItem qItem = new QItem("i");
		QItem qItem = QItem.item;//위와같은것
		//QItem qItem = item; 또는 임포트할때 static으로 임포트하면된다
		
		/*
		select * from item i where i.item_detail like
		%:itemDetail% order by i.price desc
		*/
		//메소드체인을할땐 이렇게 아래로 띄면서쓴다
		List<Item> selectedItem = 	jpaQueryFactory.select(qItem)
									.from(qItem)
									.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
									.where(qItem.itemDetail.like("%3%"))
									.orderBy(qItem.price.desc())
									.fetch();
		//fetch()메소드를 써야 이 조건에 해당하는 문구들을 갖고온다
		System.out.println("---------------(query dsl)현재 판매중 상품 갖고오기 시작--------------");
		for(Item data : selectedItem) {
			log.info(data);
		}
		System.out.println("---------------(query dsl)현재 판매중 상품 갖고오기 종료--------------");
	}
	
	public void createItemList2() {
		for(int i = 1; i<=5; i++) {
			Item item = new Item();
			item.setItemNm("상품"+i);
			item.setPrice(10000+i*100);
			item.setItemDetail("상품상세설명"+i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			//save는 db에 insert한다는 의미
			itemRepository.save(item);
		}
		
		for(int i = 6; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("상품"+i);
			item.setPrice(10000+i*100);
			item.setItemDetail("상품상세설명"+i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			//save는 db에 insert한다는 의미
			itemRepository.save(item);
		}
	}
	
	@Test
	@DisplayName("query dsl 테스트")
	public void qeurydslTest2() {
		createItemList2();
		String itemDetail = "상세";
		int price = 10200;
		String itemSellStatus = "SELL";
		// 스태틱 임포트 한 item사용하기 (근데난 스태틱임포트안함)
		QItem qItem = QItem.item;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qItem.itemDetail.like("%"+itemDetail+"%"));
		//gt는 grater than 즉, 10200보다 큰
		builder.and(qItem.price.gt(price));
		//판매 중인 상품이라면 - project 내 값을 비교한다
		if(StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
			//database 에서 비교함
			
			//builder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
		}
		// of(시작페이지,가져올페이지개수)
		Pageable pageable = PageRequest.of(1, 5);
		/*
		 	Page<Item> org.springframework.data.querydsl.
		 	QuerydslPredicateExecutor.findAll(Predicate predicate, Pageable pageable)
		 */
		
		Page<Item> allData = itemRepository.findAll(builder, pageable);
		
		// 전체 개수 확인하기
		System.out.println("-----------------------------------------");
		log.info("전체 개수 : " + allData.getTotalElements()+"개");
		
		//List<Item> org.springframework.data.domain.Slice.getContent()
		List<Item> contents = allData.getContent();
		for(Item item2 : contents) {
			log.info("item2 : "+item2);
		}
		System.out.println("-----------------------------------------");
	}
}
