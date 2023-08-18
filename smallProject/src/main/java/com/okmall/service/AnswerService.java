package com.okmall.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.okmall.entity.Answer;
import com.okmall.entity.Member;
import com.okmall.entity.Question;
import com.okmall.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	//Question 객체와 문자열(content) 를 
	//파라미터로 전달받아 Entity를 DB에 저장한다
	public Answer createAnswer(Question question , String content, Member member) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setMember(member);
		
		answerRepository.save(answer);
		return answer;
	}
	
	// 답글 조회
	public Answer getAnswerOne(Long id) {
		Answer answer = answerRepository.findById(id)
										.orElseThrow(()-> new EntityNotFoundException("답변글이 없습니다"));
		
		return answer;
	}
	
	//답글 수정
	public void modify(Answer answer , String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		answerRepository.save(answer);
	}
	
	//답글 삭제
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
	
	//답글 추천
	public void vote(Answer answer, Member member) {
		answer.getVoter().add(member);
		answerRepository.save(answer);
	}
}
