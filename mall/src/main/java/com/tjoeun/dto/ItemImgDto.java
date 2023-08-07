package com.tjoeun.dto;

import org.modelmapper.ModelMapper;

import com.tjoeun.entity.BaseEntity;
import com.tjoeun.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ItemImgDto extends BaseEntity{
	private Long id;
	
	private String imgName; // 이미 파일명 : 프로젝트 내에서 ID로 저장하는이름
		
	private String oriImgName; // 원본 이미지 파일명
	
	private String imgUrl; // 이미지 저장 경로
	
	private String repImgYn; // 대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// ItemImgDto가 화면에서 받아온 data를
	// Entity클래스인 ItemImg에 전달하는 메소드
	public static ItemImgDto of(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDto.class);
	}
	
	// BaseEntity에 있는 regTitme과 updateTime을 사용한다
	
}
