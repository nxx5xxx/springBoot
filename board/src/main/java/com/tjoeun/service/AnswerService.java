package com.tjoeun.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	//Question 객체와 문자열(content) 를 
	//파라미터로 전달받아 Entity를 DB에 저장한다
	public void createAnswer(Question question , String content, Users users) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setUsers(users);
		
		answerRepository.save(answer);
	}
}
