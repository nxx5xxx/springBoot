package com.tjoeun.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UsersFormDTO {
	//문자의 반드시입력은
	@NotEmpty(message="아이디를 반드시 입력해야합니다")
	@Size(min=4,max=20)
	private String username;
	
	@NotEmpty(message=" 비밀번호를 입력해 주세요")
	private String password;
	
	@NotEmpty(message=" 비밀번호 확인을 입력해 주세요")
	private String password2;
	
	@NotEmpty(message="이메일은 반드시 입력해 주세요")
	@Email
	private String email;
}
