package com.okmall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.okmall.dto.ReviewFormDto;
import com.okmall.dto.ReviewImgDto;
import com.okmall.entity.Item;
import com.okmall.entity.Member;
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
	
	public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList,Member member,Item item) throws Exception{
		Review review = reviewFormDto.createReview();
		review.setMember(member);
		review.setCreateDate(LocalDateTime.now());
		review.setItem(item);
		reviewRepository.save(review);
		
		for(int i=0;i<reviewImgFileList.size();i++) {
			ReviewImg reviewImg = new ReviewImg();
			reviewImg.setReview(review);
		reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));	
		}
		return review.getId();
	}
	
	public Page<Review> getList(int page, String keyword){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		return reviewRepository.findAllByKeyword(keyword, pageable);
	}
	
	public Review getQuestionOne(Long id) {
		return reviewRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
	}
	
    @Transactional(readOnly = true)
    public ReviewFormDto getReviewDtl(Long itemId){
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(itemId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for (ReviewImg reviewImg : reviewImgList) {
        	ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
        	reviewImgDtoList.add(reviewImgDto);
        }

        Review review = reviewRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
        reviewFormDto.setReviewImgDtoList(reviewImgDtoList);
        return reviewFormDto;
    }
    
	public Review getReviewOne(Long id) {
		Review review = reviewRepository.findById(id)
										.orElseThrow(EntityNotFoundException::new);
		return review;
	}
	//추천 저장
	public void vote(Review review, Member member) {
		review.getVoter().add(member);
		reviewRepository.save(review);
	}
	
	//리뷰삭제
	public void delete(Review review) {
		reviewRepository.delete(review);
	}
	
	
	//기존 이미지없이 수정만되던것
	public void modify(Review review, String title, String content) {
		review.setTitle(title);
		review.setContent(content);
		review.setModifyDate(LocalDateTime.now());
		reviewRepository.save(review);
	}
	
	//이미지까지 넣어서 수정으로 바꿈
    public Long updateReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception{
        //상품 수정
    	Review review = reviewRepository.findById(reviewFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
    	review.updateReview(reviewFormDto);
        List<Long> reviewImgIds = reviewFormDto.getReviewImgIds();

        //이미지 등록
        for(int i=0;i<reviewImgFileList.size();i++){
            reviewImgService.updateReviewImg(reviewImgIds.get(i),
            		reviewImgFileList.get(i));
        }

        return review.getId();
    }

}
