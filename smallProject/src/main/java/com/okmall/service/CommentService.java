package com.okmall.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.okmall.entity.Answer;
import com.okmall.entity.Comment;
import com.okmall.entity.Member;
import com.okmall.entity.Question;
import com.okmall.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	
	public Comment create(Question question,Member member, String content) {
		Comment c = new Comment();
		c.setContent(content);
		c.setCreateDate(LocalDateTime.now());
		c.setQuestion(question);
		c.setMember(member);
		c = commentRepository.save(c);
		return c;
	}
	
	public Comment createAnswer(Answer answer,Member member, String content) {
		Comment c = new Comment();
		c.setContent(content);
		c.setCreateDate(LocalDateTime.now());
		c.setAnswer(answer);
		c.setMember(member);
		c = commentRepository.save(c);
		return c;
	}
	
	public Optional<Comment> getComment(Long id){
		return commentRepository.findById(id);
	}
	public Comment modify(Comment c, String content) {
		System.out.println("서비스에서 content"+content);
		c.setContent(content);
		c.setModifyDate(LocalDateTime.now());
		c = commentRepository.save(c);
		return c;
		
	}
	
	public void delete(Comment c) {
		commentRepository.delete(c);
	}
}
