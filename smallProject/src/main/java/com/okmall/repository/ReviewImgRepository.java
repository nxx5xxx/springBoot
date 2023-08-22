package com.okmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.ReviewImg;

public interface ReviewImgRepository extends JpaRepository<ReviewImg,Long>{
	
	List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);
	
	//ReviewImg findByReviewId(Long reviewId);

}
