package com.tjoeun.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	private Long id;
	
	//길이
	@Column(length=200)
	private String subject;
	
	
	@Column(columnDefinition = "TEXT")
	private String content;
	private LocalDateTime createDate;
	
	//퀘스쳔이 부모가됨
	//퀘스쳔이 One 질문하나에 답글 여러개
	//아래 설정을 안해주면 양방향으로 되지않음 이렇게 해야 양방향으로 설정되어 서로의 내용에 연관관계맺음
	@OneToMany(mappedBy  = "question", cascade = CascadeType.ALL,
				orphanRemoval = true,fetch = FetchType.LAZY)
	private List<Answer> answerList = new ArrayList<>();
	
	//글쓴이
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Users users;
	
	
}
