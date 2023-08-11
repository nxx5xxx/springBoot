package com.tjoeun.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	//모두 불러오기
	public List<Question> findAll(){
		List<Question> questionList = questionRepository.findAll();
		return questionList;
		//return questionRepository.findAll();
	}
	
	//페이징처리해서 불러오기
	public Page<Question> getList(int page){
		//최근에 작성한 글 맨 위에 보이게 하기
		List<Sort.Order> sorts = new ArrayList<>();
		
		//페이지처리와 디센딩(내림차순) 정렬
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Page<Question> questionPage = questionRepository.findAll(pageable);
		return questionPage;
	}
	
	public Question getQuestionOne(Long id) {
		Question q1 = questionRepository.findById(id)
										.orElseThrow(EntityNotFoundException::new);
		return q1;
	}
	
	//질문글 DB에 저장해버리기
	public void saveQuestion(String subject , String content,Users users) {
		Question q1 = new Question();
		q1.setSubject(subject);
		q1.setContent(content);
		q1.setCreateDate(LocalDateTime.now());
		q1.setUsers(users);
		questionRepository.save(q1);
	}
	
	
}
