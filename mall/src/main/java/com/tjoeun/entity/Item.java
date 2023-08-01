package com.tjoeun.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.tjoeun.constant.ItemSellStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_id")
	private Long id; 		//상품코드
	
	@Column(nullable=false)
	private String itemNm; 	//상품명 
	
	private int price; 		//상품가격
	
	@Column(nullable=false,name="number")
	private int stockNumber;//재고수량
	
	//길이가 255개 이상인 문자열을 저장할 수 있음
	@Lob
	@Column(nullable=false)
	private String itemDetail;//제품상세설명
	
	//@Enumerated 이넘을 클래스의 멤버변수로 설정할수 있도록 하는 어노테이션
	@Enumerated(EnumType.STRING)//Ordinal 숫자
	private ItemSellStatus itemSellStatus;
	
	private LocalDateTime regTime; //등록시간
	
	private LocalDateTime updateTime;
}
