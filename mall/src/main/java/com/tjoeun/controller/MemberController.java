package com.tjoeun.controller;

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

import com.tjoeun.dto.MemberFormDTO;
import com.tjoeun.entity.Member;
import com.tjoeun.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// localhost:포트번호/member/new
	@GetMapping("/new")
	public String memberForm(MemberFormDTO memberFormDTO) {
		return "member/memberForm";
	}
	/*
	 *위와 이 아래두개는 같은것이다
	@GetMapping("/new")
	public String memberForm(Model model) {
	model.addAttribute("memberFormDTO",new MemberFormDTO());
		return "member/memberForm";
	}
	@GetMapping("/new")
	public String memberForm(MemberFormDTO memberFormDTO,Model model) {
	model.addAttribute("memberFormDTO",memberFormDTO);
		return "member/memberForm";
	}
	*/
	
	@PostMapping("/new")
	public String memberForm(@Valid MemberFormDTO memberFormDTO,BindingResult result,Model model) {
		if(result.hasErrors()) {
			log.info("축하합니다!!!!!!! 회원가입실패!");
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDTO, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//가입한 회원이 있는경우 나오는 캐치문
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		log.info(">>>>>>>>>>>>>>>>회원가입완료" + memberFormDTO);

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
