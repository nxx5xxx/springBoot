package com.tjoeun.dto;

import com.tjoeun.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
/*
 * 조회조건 1) 상품등록일
			2) 판매상태
			3) 상품명이나 등록자아이디
			4) 등록자아이디
 * 
 */
	public String searchDateType;
	
	private ItemSellStatus searchSellStatus;
	
	private String searchBy;
	
	private String searchQuery ="";
}
