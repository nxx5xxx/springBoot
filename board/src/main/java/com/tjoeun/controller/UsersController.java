package com.tjoeun.controller;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.dto.UsersFormDTO;
import com.tjoeun.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService usersService;
	
	@RequestMapping(value="/signup" , method = RequestMethod.GET)
	public String signup(UsersFormDTO usersFormDTO) {
		return "signup_form";
	}
	@PostMapping("/signup")
	public String signup(@Valid UsersFormDTO usersFormDTO,BindingResult result) {
		// 비밀번호와 비밀번호 확인 값이 일치하는지 확인하기
		if(!usersFormDTO.getPassword().equals(usersFormDTO.getPassword2())) {
			//커스텀발리데이트 추가
			result.rejectValue("password2", "passwordInCorrect","비밀번호가 일치하지 않습니다ㅡㅡ");
		}
		if(result.hasErrors()) {
			return "signup_form";			
		}
		//아이디 중복확인
		try {
			// 회원정보가 제대로 입력된 경우
			usersService.createUsers(usersFormDTO.getUsername(), usersFormDTO.getPassword(), usersFormDTO.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			result.reject("signupFailed","이미 가입한 회원입니다");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
			result.reject("signupFailed",e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
}
