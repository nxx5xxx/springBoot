package com.okmall.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.okmall.dto.ReviewFormDto;
import com.okmall.entity.Review;
import com.okmall.entity.ReviewImg;
import com.okmall.repository.ReviewImgRepository;
import com.okmall.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final ReviewImgRepository reviewImgRepository;
	private final ReviewImgService reviewImgService;
	//모두 불러오기
	public List<Review> findAll(){
		return reviewRepository.findAll();
	}
	
	public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception{
		Review review = reviewFormDto.createReview();
		reviewRepository.save(review);
		
		for(int i=0;i<reviewImgFileList.size();i++) {
			ReviewImg reviewImg = new ReviewImg();
			reviewImg.setReview(review);
		reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));	
		}
		return review.getId();
	}

}
