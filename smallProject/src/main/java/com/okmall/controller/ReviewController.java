package com.okmall.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.okmall.dto.OrderHistDto;
import com.okmall.dto.ReviewFormDto;
import com.okmall.entity.Member;
import com.okmall.entity.Order;
import com.okmall.entity.Review;
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
	
	@GetMapping("/new")
	public String reviewForm(Model model, ReviewFormDto reviewFormDto,Principal principal) {
		model.addAttribute("reviewFormDto", reviewFormDto);
        List<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName());
        model.addAttribute("orders", ordersHistDtoList);
		
		
		return "review/reviewForm";
	}
	
	//주문번호 불러와서 뿌려주고
	//해당 주문번호로 쓴 글이 있을시 그 주문번호는 빼고 뿌려주기
	
	@PostMapping("/new")
	public String reviewForm(Model model,@Valid ReviewFormDto reviewFormDto,BindingResult result,
				@RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,Principal principal,
				@RequestParam("order_btn") int order_btn) {
		//System.out.println("라디오버튼테스트"+ order_btn);
		//잘받아와짐 이제 그 주문번호 상태 바꿔주기
		
		if(result.hasErrors()) {
			return "review/reviewForm";
		}
        try {
        	Member member = memberService.getMember(principal.getName());
            reviewService.saveReview(reviewFormDto, reviewImgFileList , member );
            orderService.writeReview(order_btn);
        } catch (Exception e){
            model.addAttribute("errorMessage", "후기 등록 중 에러가 발생하였습니다."+e);
            return "review/reviewForm";
        }

		return "redirect:/";
	}
	
	@GetMapping("list")
	public String reviewList(Model model,
					@RequestParam(value="page", defaultValue="0") int page,
					@RequestParam(value="keyword", defaultValue="") String keyword,
					Principal principal
					) {
		Page<Review> paging = reviewService.getList(page, keyword);
		model.addAttribute("paging",paging);
		model.addAttribute("keyword",keyword);
		try {
		model.addAttribute("loginChk",principal.getName());
		} catch (Exception e) {
		model.addAttribute("loginChk",null);
		}
		return "review/review_list";
	}
}
