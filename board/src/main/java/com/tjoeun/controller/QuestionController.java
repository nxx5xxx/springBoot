package com.tjoeun.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.AnswerFormDTO;
import com.tjoeun.dto.QuestionFormDTO;
import com.tjoeun.entity.Question;
import com.tjoeun.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	//리스폰스바디를쓰면 레스트컨트롤러와 같은역할을한다
	//레스트컨트롤러는 해당 컨트롤러 전체가 되므로
	//메소드별로 하고싶으면 리스폰스바디
	
//	@GetMapping("/question/list")
//	@ResponseBody
//	public String list() {
//		return "<h1>질문글 목록</h1>";
//	}
	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = questionService.findAll();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id,Model model,Question question,AnswerFormDTO answerFormDTO) {
		question = questionService.getQuestionOne(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String createQuestion(QuestionFormDTO questionFormDTO) {
		return "question_form";
	}
	@PostMapping("/create")
	public String createQuestion(@Valid QuestionFormDTO questionFormDTO,BindingResult result) {
		//질문db에 저장하기 : 서비스의 메소드 호출
		if(result.hasErrors()) {
			return "question_form";		
		}
		questionService.saveQuestion(questionFormDTO.getSubject(), questionFormDTO.getContent());
		
		return "redirect:/question/list";
	}
}
