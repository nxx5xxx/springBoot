package com.tjoeun.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentFormDTO {
	@NotEmpty(message="글 내용은 반드시 입력하세요")
	private String content;
	
	
}
