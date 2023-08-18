package com.okmall.controller;

import java.security.Principal;
import java.util.Optional;

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

import com.okmall.dto.CommentFormDto;
import com.okmall.entity.Comment;
import com.okmall.entity.Member;
import com.okmall.entity.Question;
import com.okmall.service.CommentService;
import com.okmall.service.MemberService;
import com.okmall.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	private final CommentService commentService;
	private final QuestionService questionService;
	private final MemberService memberService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create/question/{id}")
	public String createComment(@PathVariable("id") Long id,CommentFormDto commentFormDto) {
		return "notice/comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/question/{id}")
	public String createComment(@PathVariable("id") Long id,@Valid CommentFormDto commentFormDto,
								BindingResult result,Principal principal,Model model) {
		Question question =  questionService.getQuestionOne(id);
		Member member = memberService.getMember(principal.getName());
		
		if(result.hasErrors()) {			
			return "notice/comment_form";
		}
		
		Comment c = commentService.create(question, member, commentFormDto.getContent());
		return String.format("redirect:/question/detail/%s",c.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyComment(@PathVariable("id") Long id,CommentFormDto commentFormDto,Principal principal) {
//		Comment comment = commentService.getComment(id)
//												  .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한 없음"));
		Optional<Comment> comment = commentService.getComment(id);
		//isPresent Optional이 null값이 아니고 Comment객체가 있다면 Comment객체를 꺼내오라는뜻
		if(comment.isPresent()) {
			Comment c = comment.get();
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한 없음");
			}
			commentFormDto.setContent(c.getContent());
		}	
		return "notice/comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyComment(@Valid CommentFormDto commentFormDto , BindingResult result,
								Principal principal,@PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return "notice/comment_form";
		}
		
		Optional<Comment> comment = commentService.getComment(id);
		//isPresent Optional이 null값이 아니고 Comment객체가 있다면 Comment객체를 꺼내오라는뜻
		if(comment.isPresent()) {
			Comment c = comment.get();
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한 없음");
			}
			//commentFormDto.setContent(c.getContent());
			c = commentService.modify(c, commentFormDto.getContent());
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"수정권한 없음");
//			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		}
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteComment(@PathVariable("id") Long id,Principal principal) {
		Optional<Comment> comment = commentService.getComment(id);
		if(comment.isPresent()) {
			Comment c = comment.get();
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한 없음");
			}
			commentService.delete(c);
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"삭제권한 없음");
		}
	}
}
