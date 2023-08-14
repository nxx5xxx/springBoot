package com.tjoeun.controller;

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

import com.tjoeun.dto.AnswerFormDTO;
import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.service.AnswerService;
import com.tjoeun.service.QuestionService;
import com.tjoeun.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final UsersService usersService;
	// id < -- Question 의 id
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Long id,Model model,@Valid AnswerFormDTO answerFormDTO, BindingResult result,
			Principal principal) {
		Question question = questionService.getQuestionOne(id);
		//불러온 question entity에 대한 답변 (answer) 저장 하기 
		if(result.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
			//return String.format("redirect:/question/detail/%s",id);
		}
		//principal이 현재 로그인한 유저의 이름을 갖고온다
		Users users = usersService.getUsers(principal.getName());
		Answer answer = answerService.createAnswer(question, answerFormDTO.getContent(),users);
		
		//model.addAttribute("question", question);
		//String.format은 서식문자열을 지정할수있다 printf마냥
		return String.format("redirect:/question/detail/%s#answer_%s",id,answer.getId());
	}
	
	
	//수정
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyAnswer(@PathVariable Long id,AnswerFormDTO answerFormDTO,Principal principal) {
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " 수정권한 없음");
		}
		answerFormDTO.setContent(answer.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerFormDTO answerFormDTO , BindingResult result,
								@PathVariable("id") Long id,Principal principal) {
		if(result.hasErrors()) {
			return "answer_form";
		}
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		answerService.modify(answer, answerFormDTO.getContent());
		//return "redirect:/question/detail/"+answer.getQuestion().getId();
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable Long id) {
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getUsers().getUsername().equals(principal.getName())) {
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
		Users users = usersService.getUsers(principal.getName());

		answerService.vote(answer, users);
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
}
