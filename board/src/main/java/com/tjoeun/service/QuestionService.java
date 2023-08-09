package com.tjoeun.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.tjoeun.entity.Question;
import com.tjoeun.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	
	public List<Question> findAll(){
		List<Question> questionList = questionRepository.findAll();
		return questionList;
		//return questionRepository.findAll();
	}
	
	public Question getQuestionOne(Long id) {
		Question q1 = questionRepository.findById(id)
										.orElseThrow(EntityNotFoundException::new);
		return q1;
	}
	
	//질문글 DB에 저장해버리기
	public void saveQuestion(String subject , String content) {
		Question q1 = new Question();
		q1.setSubject(subject);
		q1.setContent(content);
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);
	}
}
