package com.tjoeun.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="comment_id")
	private Long id;
	
	//글쓴이
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Users users;
	
	//내용
	@Column(columnDefinition = "TEXT")
	private String content;
	
	//등록일
	private LocalDateTime createDate;
	
	//수정날짜
	private LocalDateTime modifyDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="answer_id")
	private Answer answer;
	
	// 질문글의 id 얻어오기
	public Long getQuestionId() {
		Long id  = null;
		
		// 질문글에 댓글 다는 경우
		if(this.question != null) {
			id = this.question.getId();
		// 답글에 댓글 다는 경우 
		}else if(this.answer != null) {
			id = this.answer.getQuestion().getId();
		}
		return id;
	}
}
