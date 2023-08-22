package com.okmall.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.okmall.dto.OrderHistDto;
import com.okmall.dto.ReviewCommentFormDto;
import com.okmall.dto.ReviewFormDto;
import com.okmall.entity.Item;
import com.okmall.entity.Member;
import com.okmall.entity.Review;
import com.okmall.service.ItemService;
import com.okmall.service.MemberService;
import com.okmall.service.OrderService;
import com.okmall.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
	private final ReviewService reviewService;
	private final MemberService memberService;
	private final OrderService orderService;
	private final ItemService itemService;
	
	//리뷰등록
	@GetMapping("/new")
	public String reviewForm(Model model, ReviewFormDto reviewFormDto,Principal principal) {
		model.addAttribute("reviewFormDto", reviewFormDto);
        List<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName());
        model.addAttribute("orders", ordersHistDtoList);
		
		
		return "review/reviewForm";
	}
	
	@PostMapping("/new")
	public String reviewForm(Model model,@Valid ReviewFormDto reviewFormDto,BindingResult result,
				@RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,Principal principal,
				@RequestParam("order_btn") int order_btn) {
		
		if(result.hasErrors()) {
			return "review/reviewForm";
		}
        try {
        	Member member = memberService.getMember(principal.getName());
        	Item item = itemService.getItem((long)order_btn);
            reviewService.saveReview(reviewFormDto, reviewImgFileList , member ,item);
            orderService.writeReview(order_btn);
        } catch (Exception e){
            model.addAttribute("errorMessage", "후기 등록 중 에러가 발생하였습니다."+e);
            return "review/reviewForm";
        }

		return "redirect:/";
	}
	
	//수정 getReviewDtl
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(ReviewFormDto reviewFormDto,
								@PathVariable("id") Long id,
								Principal principal,Model model) {
		//principal.getName() = 현재 로그인아이디

        try {
        	reviewFormDto = reviewService.getReviewDtl(id);
            model.addAttribute("reviewFormDto", reviewFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("reviewFormDto", new ReviewFormDto());
            return "review/reviewForm";
        }

		//기존 이름만 바꾸던 구문 
//		Review review = reviewService.getQuestionOne(id);
//		if(!review.getMember().getEmail().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "수정권한 없음");
//		}
//		reviewFormDto.setTitle(review.getTitle());
//		reviewFormDto.setContent(review.getContent());
//		reviewFormDto.setReviewImgDtoList(review.getReviewImgList()); // review.getReviewImgList()
		return "review/reviewForm";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid ReviewFormDto reviewFormDto,BindingResult result,
								Principal principal,@RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,
								@PathVariable Long id,Model model) {
		if(result.hasErrors()) {
			return "review/reviewForm";
		}
		
        try {
            reviewService.updateReview(reviewFormDto, reviewImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "리뷰 수정 중 에러가 발생하였습니다.");
            return "review/reviewForm";
        }
		return "redirect:/review/detail/"+id;
	}
	
	
	//검색, 페이징처리 추가
	@GetMapping("list")
	public String reviewList(Model model,
					@RequestParam(value="page", defaultValue="0") int page,
					@RequestParam(value="keyword", defaultValue="") String keyword,
					Principal principal
					) {
		Page<Review> paging = reviewService.getList(page, keyword);
		model.addAttribute("paging",paging);
		model.addAttribute("keyword",keyword);
		//구매내역이 있어야 후기작성하도록 추가
		List<OrderHistDto> ordersHistDtoList;
		try {
			ordersHistDtoList = orderService.getOrderList(principal.getName());
			
		} catch (Exception e) {
			ordersHistDtoList = null;
		}
		
		model.addAttribute("ordersHistDtoList", ordersHistDtoList);
		try {
		model.addAttribute("loginChk",principal.getName());
		} catch (Exception e) {
		model.addAttribute("loginChk",null);
		}
		return "review/review_list";
	}
	
	//상세보기
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id,Model model,Review review,ReviewCommentFormDto reviewCommentFormDto,Principal principal) {
		model.addAttribute("review", reviewService.getQuestionOne(id));
		ReviewFormDto reviewFormDto = reviewService.getReviewDtl(id);
		model.addAttribute("reviewImg", reviewFormDto);
		try {
			model.addAttribute("loginChk",principal.getName());
		} catch (Exception e) {
			model.addAttribute("loginChk",null);
		}
		return "review/review_detail";
	}
	
	//추천 저장ReviewService reviewService
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String reviewVote(@PathVariable Long id , Principal principal) {
		Review review = reviewService.getReviewOne(id);
		Member member = memberService.getMember(principal.getName());
		reviewService.vote(review, member);
		return String.format("redirect:/review/detail/%s",id);
	}
	
	//삭제
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String reviewDelete(@PathVariable Long id , Principal principal) {
		Review review = reviewService.getReviewOne(id);
		if(!review.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "삭제 권한 없음");
		}
		reviewService.delete(review);
		return "redirect:/";
	}
	

}
