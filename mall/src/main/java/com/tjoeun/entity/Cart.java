package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class Cart {
	
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// cart테이블과 member 테이블 1:1 관계
	// 1:1 관계
	//LAZY설정을 안해주면 즉시로딩이 기본값이다
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name= "member_id")
	private Member member;
	
	
}
