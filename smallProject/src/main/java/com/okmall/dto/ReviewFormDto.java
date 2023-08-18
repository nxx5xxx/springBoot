package com.okmall.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.okmall.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewFormDto {
	private Long id;
	
	@NotBlank(message = "제목은 반드시 입력해주세요")
	private String title;
	
	@NotBlank(message = "내용은 반드시 입력해주세요")
	private String content;
	
	private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
	
	private List<Long> reviewImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Review createReview() {
		return modelMapper.map(this, Review.class); 
	}
	
	public static ReviewFormDto of(Review review) {
		return modelMapper.map(review, ReviewFormDto.class);
	}

}
