package com.tjoeun.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.tjoeun.dto.MemberFormDto;
import com.tjoeun.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value=false)//롤백을 꺼둠
class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDto memberFormDTO = new MemberFormDto();
		memberFormDTO.setEmail("theDog@naver.com");
		memberFormDTO.setName("강아지");
		memberFormDTO.setAddress("정발산");
		memberFormDTO.setPassword("1234");
		
		return Member.createMember(memberFormDTO, passwordEncoder);
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void regMemberTest() {
		Member member = createMember();
		Member savedMember = memberService.saveMember(member);
		
		assertEquals(member.getEmail(), savedMember.getEmail());
		assertEquals(member.getName(), savedMember.getName());
		assertEquals(member.getAddress(), savedMember.getAddress());
		assertEquals(member.getPassword(), savedMember.getPassword());
		assertEquals(member.getRole(), savedMember.getRole());
	}

}
