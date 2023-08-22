package com.okmall.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.okmall.dto.ReviewCommentFormDto;
import com.okmall.entity.Member;
import com.okmall.entity.Review;
import com.okmall.entity.ReviewComment;
import com.okmall.service.MemberService;
import com.okmall.service.ReviewCommentService;
import com.okmall.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviewComment")
public class ReviewCommentController {
	
	private final MemberService memberService;
	private final ReviewService reviewService;
	private final ReviewCommentService reviewCommentService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Long id,Model model,@Valid ReviewCommentFormDto reviewCommentFormDto, BindingResult result,
			Principal principal) {
		Review review = reviewService.getQuestionOne(id);
		if(result.hasErrors()) {
			model.addAttribute("review", review);
			return "review/review_detail";
		}
		//principal이 현재 로그인한 유저의 이름을 갖고온다
		Member member = memberService.getMember(principal.getName());
		ReviewComment reviewComment = reviewCommentService.createAnswer(review, reviewCommentFormDto.getContent(),member);
		
		//model.addAttribute("question", question);
		//String.format은 서식문자열을 지정할수있다 printf마냥
		return String.format("redirect:/review/detail/%s#reviewComment_%s",id,reviewComment.getId());
	}
	//추천기능
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(@PathVariable Long id , Principal principal) {
		ReviewComment reviewComment = reviewCommentService.getAnswerOne(id);
		Member member = memberService.getMember(principal.getName());

		reviewCommentService.vote(reviewComment, member);
		return String.format("redirect:/review/detail/%s#reviewComment_%s",reviewComment.getReview().getId(),reviewComment.getId());
	}
	
	//수정
	//수정
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyAnswer(@PathVariable Long id,ReviewCommentFormDto reviewCommentFormDto,Principal principal) {
		ReviewComment reviewComment = reviewCommentService.getAnswerOne(id);
		if(!reviewComment.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " 수정권한 없음");
		}
		reviewCommentFormDto.setContent(reviewComment.getContent());
		return "/review/review_comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid ReviewCommentFormDto reviewCommentFormDto , BindingResult result,
								@PathVariable("id") Long id,Principal principal) {
		if(result.hasErrors()) {
			return "review/review_comment_form";
		}
		ReviewComment reviewComment = reviewCommentService.getAnswerOne(id);
		if(!reviewComment.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		reviewCommentService.modify(reviewComment, reviewCommentFormDto.getContent());
		//return "redirect:/question/detail/"+answer.getQuestion().getId();
		return String.format("redirect:/review/detail/%s#reviewComment_%s",reviewComment.getReview().getId(),reviewComment.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable Long id) {
		ReviewComment reviewComment = reviewCommentService.getAnswerOne(id);
		if(!reviewComment.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		reviewCommentService.delete(reviewComment);
		return "redirect:/review/detail/"+reviewComment.getReview().getId();
	}
}
