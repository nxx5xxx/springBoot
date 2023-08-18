package com.okmall.controller;

import java.security.Principal;

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
import org.springframework.web.server.ResponseStatusException;

import com.okmall.dto.AnswerFormDto;
import com.okmall.dto.QuestionFormDto;
import com.okmall.entity.Member;
import com.okmall.entity.Question;
import com.okmall.service.MemberService;
import com.okmall.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	private final MemberService memberService;
	
	//리스폰스바디를쓰면 레스트컨트롤러와 같은역할을한다
	//레스트컨트롤러는 해당 컨트롤러 전체가 되므로
	//메소드별로 하고싶으면 리스폰스바디
	
//	@GetMapping("/question/list")
//	@ResponseBody
//	public String list() {
//		return "<h1>질문글 목록</h1>";
//	}
	
//	@GetMapping("/list")
//	public String list(@RequestParam(value="page", defaultValue="0") int page ,Model model) {
//		Page<Question> paging = questionService.getList(page);
//		model.addAttribute("paging", paging);
//		//model.addAttribute("page",page);
//		return "question_list";
//	}
	// 검색
	// @RequestParam(value="keyword",defaultValue="") String Keyword <-- 추가함
	@GetMapping("/list")
	public String list(Model model,
						@RequestParam(value="page", defaultValue="0") int page,
						@RequestParam(value="keyword", defaultValue="") String keyword,
						Principal principal
						) {
		Page<Question> paging = questionService.getList(page, keyword);
		model.addAttribute("paging",paging);
		model.addAttribute("keyword",keyword);
		try {
			model.addAttribute("loginChk",principal.getName());
		} catch (Exception e) {
			model.addAttribute("loginChk",null);
		}
		
		return "notice/question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id,Model model,Question question,AnswerFormDto answerFormDto,Principal principal) {
		//PMD수정 전
		//question = questionService.getQuestionOne(id);
		//model.addAttribute("question", question);
		//PMD수정 후
		model.addAttribute("question", questionService.getQuestionOne(id));
		try {
			model.addAttribute("loginChk",principal.getName());
		} catch (Exception e) {
			model.addAttribute("loginChk",null);
		}
		return "notice/question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String createQuestion(QuestionFormDto questionFormDto) {
		return "notice/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createQuestion(@Valid QuestionFormDto questionFormDto,BindingResult result,Principal principal) {
		//질문db에 저장하기 : 서비스의 메소드 호출
		if(result.hasErrors()) {
			return "notice/question_form";		
		}
		System.out.println("회원명"+principal.getName());
		Member member = memberService.getMember(principal.getName());
		questionService.saveQuestion(questionFormDto.getSubject(), questionFormDto.getContent(),member);
		
		return "redirect:/question/list";
	}
	
	//sec:authorize에 넣은값과 같아야한다
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionFormDto questionFormDto,
								@PathVariable("id") Long id,
								Principal principal) {
		//principal.getName() = 현재 로그인아이디
		Question question = questionService.getQuestionOne(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "수정권한 없음");
		}
		questionFormDto.setSubject(question.getSubject());
		questionFormDto.setContent(question.getContent());
		return "notice/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionFormDto questionFormDto,BindingResult result,
								Principal principal,@PathVariable Long id) {
		if(result.hasErrors()) {
			return "notice/question_form";
		}
		Question question = questionService.getQuestionOne(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "수정 권한 없음");
		}
		questionService.modify(question, questionFormDto.getSubject(), questionFormDto.getContent());
		return "redirect:/question/detail/"+id;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable Long id , Principal principal) {
		Question question = questionService.getQuestionOne(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "삭제 권한 없음");
		}
		questionService.delete(question);
		return "redirect:/";
	}
	
	//추천 저장
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(@PathVariable Long id , Principal principal) {
		Question question = questionService.getQuestionOne(id);
		Member member = memberService.getMember(principal.getName());
		questionService.vote(question, member);
		return String.format("redirect:/question/detail/%s",id);
	}
	
}
