package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class CartItem extends BaseEntity{

	
	@Id
	@Column(name = "cart_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// cart_item테이블과 cart 테이블 N:1관계
	@JoinColumn(name="cart_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cart cart;
	
	@JoinColumn(name="item_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	private int count;
}
