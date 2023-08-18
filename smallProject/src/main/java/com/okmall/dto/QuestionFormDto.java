package com.okmall.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class QuestionFormDto {

	//private Long id;
	@NotEmpty(message = "글 제목은 반드시 입력해주세요")
	@Size(max=500)
	private String subject;
	
	@NotEmpty(message = "글 내용은 반드시 입력해주세요")
	private String content;
	
	//private LocalDateTime createDate;	
}
