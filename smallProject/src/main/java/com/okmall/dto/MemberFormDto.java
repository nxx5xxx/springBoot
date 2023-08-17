package com.okmall.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MemberFormDto {

	// 회원가입 할 때 입력해야되는 내용을
	// MemberFormDTO 클래스의 멤버변수로 지정함
	// name,email,password, address
	
	@NotBlank(message="이름은 공백일수 없습니다")
	private String name;
	
	@NotEmpty(message="이메일은 비어있을수 없습니다")
	@Email(message="올바른 이메일 형식이 아니다")
	private String email;
	
	@Length(min=4,max=20,message="비밀번호는 4-20글자로 해주삼")
	private String password;
	
	@NotEmpty(message="주소는 반드시 입력해 주세요")
	private String address;
}
