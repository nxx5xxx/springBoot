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

import com.okmall.dto.AnswerFormDto;
import com.okmall.entity.Answer;
import com.okmall.entity.Member;
import com.okmall.entity.Question;
import com.okmall.service.AnswerService;
import com.okmall.service.MemberService;
import com.okmall.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final MemberService memberService;
	// id < -- Question 의 id
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Long id,Model model,@Valid AnswerFormDto answerFormDto, BindingResult result,
			Principal principal) {
		Question question = questionService.getQuestionOne(id);
		//불러온 question entity에 대한 답변 (answer) 저장 하기 
		if(result.hasErrors()) {
			model.addAttribute("question", question);
			return "notice/question_detail";
			//return String.format("redirect:/question/detail/%s",id);
		}
		//principal이 현재 로그인한 유저의 이름을 갖고온다
		Member member = memberService.getMember(principal.getName());
		Answer answer = answerService.createAnswer(question, answerFormDto.getContent(),member);
		
		//model.addAttribute("question", question);
		//String.format은 서식문자열을 지정할수있다 printf마냥
		return String.format("redirect:/question/detail/%s#answer_%s",id,answer.getId());
	}
	
	
	//수정
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyAnswer(@PathVariable Long id,AnswerFormDto answerFormDto,Principal principal) {
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " 수정권한 없음");
		}
		answerFormDto.setContent(answer.getContent());
		return "notice/answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerFormDto answerFormDto , BindingResult result,
								@PathVariable("id") Long id,Principal principal) {
		if(result.hasErrors()) {
			return "notice/answer_form";
		}
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		answerService.modify(answer, answerFormDto.getContent());
		//return "redirect:/question/detail/"+answer.getQuestion().getId();
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable Long id) {
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		answerService.delete(answer);
		return "redirect:/question/detail/"+answer.getQuestion().getId();
	}
	//추천 저장
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(@PathVariable Long id , Principal principal) {
		Answer answer = answerService.getAnswerOne(id);
		Member member = memberService.getMember(principal.getName());

		answerService.vote(answer, member);
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
}
