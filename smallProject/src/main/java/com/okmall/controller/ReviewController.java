package com.okmall.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.okmall.dto.ReviewFormDto;
import com.okmall.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/new")
	public String reviewForm(Model model, ReviewFormDto reviewFormDto) {
		model.addAttribute("reviewFormDto", reviewFormDto);
		return "review/reviewForm";
	}
	@PostMapping("/new")
	public String reviewForm(Model model,@Valid ReviewFormDto reviewFormDto,BindingResult result,
				@RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList) {
		if(result.hasErrors()) {
			return "review/reviewForm";
		}
        try {
            reviewService.saveReview(reviewFormDto, reviewImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다."+e);
            return "review/reviewForm";
        }
		return "redirect:/";
	}
	
}
