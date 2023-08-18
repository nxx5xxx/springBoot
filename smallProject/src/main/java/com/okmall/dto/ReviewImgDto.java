package com.okmall.dto;

import org.modelmapper.ModelMapper;

import com.okmall.entity.ReviewImg;

public class ReviewImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ReviewImgDto of(ReviewImg reviewImg) {
		return modelMapper.map(reviewImg, ReviewImgDto.class);
	}

}
