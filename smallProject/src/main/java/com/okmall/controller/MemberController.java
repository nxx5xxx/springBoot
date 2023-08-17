package com.okmall.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.okmall.dto.MemberFormDto;
import com.okmall.entity.Member;
import com.okmall.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/members")
@Log4j2
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@GetMapping("/new")
	public String memberForm(MemberFormDto memberFormDTO) {
		return "member/memberForm";
	}

	
	@PostMapping("/new")
	public String memberForm(@Valid MemberFormDto memberFormDto,BindingResult result,Model model) {
		if(result.hasErrors()) {
			log.info("축하합니다!!!!!!! 회원가입실패!");
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//가입한 회원이 있는경우 나오는 캐치문
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		log.info(">>>>>>>>>>>>>>>>회원가입완료" + memberFormDto);

		return "redirect:/";
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	public String login() {
		return "member/memberLoginForm";
	}
	
	@RequestMapping(value="/login/error" ,method = RequestMethod.GET)
	public String loginError (Model model) {
		model.addAttribute("loginErrorMessage", "이메일 또는 비밀번호를 확인하시오");
		return "member/memberLoginForm";
	}
	
	
}
