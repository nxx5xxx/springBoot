package com.tjoeun.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.AnswerFormDTO;
import com.tjoeun.entity.Question;
import com.tjoeun.service.AnswerService;
import com.tjoeun.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	// id < -- Question 의 id
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Long id,Model model,@Valid AnswerFormDTO answerFormDTO, BindingResult result) {
		Question question = questionService.getQuestionOne(id);
		//불러온 question entity에 대한 답변 (answer) 저장 하기 
		if(result.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
			//return String.format("redirect:/question/detail/%s",id);
		}
		answerService.createAnswer(question, answerFormDTO.getContent());
		
		//model.addAttribute("question", question);
		//String.format은 서식문자열을 지정할수있다 printf마냥
		return String.format("redirect:/question/detail/%s",id);
	}
}
