package com.tjoeun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.repository.AnswerRepository;
import com.tjoeun.repository.QuestionRepository;
import com.tjoeun.service.QuestionService;
import com.tjoeun.service.UsersService;

@SpringBootTest
//@Transactional
class BoardApplicationTest {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private  UsersService usersService;
	
	@Test
	@DisplayName("질문테스트-1")
	void questionTest() {
		Question q1 = new Question();
		q1.setSubject("question1");
		q1.setContent("스프링부트는 무엇입니까");
		//현재날자
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("질문글 2입니다");
		q2.setContent("QueryDsl는 무엇입니까");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}
	
	@Test
	@DisplayName("조회테스트")
	void selectAllTest() {
		//findAll은 모든컬럼조회
		List<Question> questionList = questionRepository.findAll();
		//같은지 비교하는것
		assertEquals(2, questionList.size());
		Question q1 = questionList.get(0);
		assertEquals("question1",q1.getSubject());
		
	}
	
	@Test
	@DisplayName("조회테스트2")
	void selectTest2() {
		Optional<Question> questionOne = questionRepository.findById((long)2);
		
		if(questionOne.isPresent()) {
			Question q1 = questionOne.get();
			
			assertEquals("질문글 2입니다",q1.getSubject());
		}
	}
	
	@Test
	@DisplayName("조회테스트3")
	void selectTest3() {
		//where and문법
		Question q1 = questionRepository.findBySubjectAndContent("question1","스프링부트는 무엇입니까");
		assertEquals(1,q1.getId());
		
	}
	
	@Test
	@DisplayName("조회테스트4")
	void selectTest4() {
		//like문법
		List<Question> questionList = questionRepository.findBySubjectLike("que%%");
		assertEquals(1,questionList.get(0).getId());
		
	}
	
	@Test
	@DisplayName("수정테스트1")
	void updateTest1() {
		Optional<Question> q1 = questionRepository.findById((long)1);
		
		//안에 값이 맞으면 true 아니면 false - 반환값은 없다
		// 그럼 어썰트는 테스트에서만 사용하는건가?
		assertTrue(q1.isPresent());
		
		Question question = q1.get();
		question.setSubject("Spring Board");
		//바뀐값을 저장
		questionRepository.save(question);
		
		
	}
	
	@Test
	@DisplayName("삭제 테스트1")
	void deleteTest1() {
		//count는 레코드개수를 반환한다
		//현재 2개 값이 있으므로 2를반환
		assertEquals(2,questionRepository.count());
		
		Optional<Question> q1 = questionRepository.findById((long)1);
		Question question = q1.get();
		//아이디 1인값 삭제
		questionRepository.delete(question);
		
		assertEquals(1,questionRepository.count());
	}
	
	@Test
	@DisplayName("답변 테스트1")
	void answerTest() {
		//DB에서 먼저 2번 질문글 갖고옴
		//orElseThrow를 씀으로써 Optional을 뺄수있다
		//null값이면 예외를 발생시키게끔하여 가능한것
		Question q1 = questionRepository.findById((long)2)
										.orElseThrow(EntityNotFoundException::new);
		
		//옵셔널을 없애서 옵셔널에서 꺼내는 과정을생략가능
		//assertTrue(q1.isPresent());
		//Question question = q1.get();
		
		Answer answer1 = new Answer();
		answer1.setContent("Querydsl는 정적 타입을 사용하여 SQL과 같은 Query를 생성할 수 있도록 해주는 Open Source Framework입니다");
		//어떤 질문글에 대한 답변글인지를 표시
		answer1.setQuestion(q1);
		answer1.setCreateDate(LocalDateTime.now());
		answerRepository.save(answer1);
	}
	@Test
	@DisplayName("답변글 조회 테스트(select)-1")
	void answerTest2() {
		//어느글에대한 답글인지 조회
		Answer answer1 = answerRepository.findById((long)1)
										 .orElseThrow(EntityNotFoundException::new);
		assertEquals(2,answer1.getQuestion().getId());
	}
	
	@Test
	@DisplayName("질문글에대한 답변글 조회 테스트-1")
	void questionAnswerTest1() {
		Question q1 = questionRepository.findById((long)2)
										.orElseThrow();
		
		Answer answer1 = answerRepository.findById((long)1)
										 .orElseThrow(EntityNotFoundException::new);
		String answer = "Querydsl는 정적 타입을 사용하여 SQL과 같은 Query를 생성할 수 있도록 해주는 Open Source Framework입니다";
		assertEquals(answer,answer1.getContent());
		
		// answer1 답변글에 있는 질문글의 answer1 Entity의 question_id가 q1 Entity의 question_id와 같은가
		assertEquals(answer1.getQuestion().getId(),q1.getId());
	}
	/* 질문글 더미 한꺼번에 올리기 */
	@Test
	@DisplayName("질문글 더미데이터 500")
	void uploadBoardTeest() {
		for(int i=0;i<=500;i++) {
			//스트링포맷은 서식지정자 형식으로 사용가능하다 - 03은 세자리
			String subject = String.format("테스트 게시글 : [%03d]", i);
			String content = String.format("여기는 테스트 게시글 [%03d] 입니다", i);
			questionService.saveQuestion(subject, content, null );
		}
	}
}
