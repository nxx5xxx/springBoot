package com.okmall.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.okmall.entity.Member;
import com.okmall.entity.Review;
import com.okmall.entity.ReviewComment;
import com.okmall.repository.ReviewCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {
	private final ReviewCommentRepository reviewCommentRepository;
	
	public ReviewComment createAnswer(Review review , String content, Member member) {
		ReviewComment reviewComment = new ReviewComment();
		reviewComment.setContent(content);
		reviewComment.setCreateDate(LocalDateTime.now());
		reviewComment.setReview(review);
		reviewComment.setMember(member);
		
		reviewCommentRepository.save(reviewComment);
		return reviewComment;
	}
	
	public ReviewComment getAnswerOne(Long id) {
		ReviewComment reviewComment = reviewCommentRepository.findById(id)
										.orElseThrow(()-> new EntityNotFoundException("답변글이 없습니다"));
		
		return reviewComment;
	}
	
	//답글 추천
	public void vote(ReviewComment reviewComment, Member member) {
		reviewComment.getVoter().add(member);
		reviewCommentRepository.save(reviewComment);
	}
	
	//답글 수정
	public void modify(ReviewComment reviewComment , String content) {
		reviewComment.setContent(content);
		reviewComment.setModifyDate(LocalDateTime.now());
		reviewCommentRepository.save(reviewComment);
	}
	
	//답글 삭제
	public void delete(ReviewComment reviewComment) {
		reviewCommentRepository.delete(reviewComment);
	}
}
