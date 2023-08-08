package com.tjoeun.service;

import java.util.Scanner;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tjoeun.entity.Member;
import com.tjoeun.repository.MemberRepositoryTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class MemberLoginTest {
	@Autowired
	private MemberRepositoryTest memberRepository;
	public Member loadUserByUsername(String email) {
		Member member = memberRepository.findByEmail(email);
		System.out.println("----------------------------------------------------------------------------");
		  if(member==null) {
			throw new UsernameNotFoundException("----------------해당 이메일로 가입한 회원이 없습니다----------------"+email);
		  }
		System.out.println("----------------------------------------------------------------------------");
		
		log.info(">>>>>>>>>>>>>>> memberLoginTest" + member.getEmail());
		
		return 	member;
	}
	@Test
	@DisplayName("회원 로그인 테스트")
	void memberLoginTest() {
		Scanner sc = new Scanner(System.in);
		System.out.println("로그인 테스트 할 아이디를 입력해주세요");
		String testEmail = sc.nextLine();
		Member member = loadUserByUsername(testEmail);
		System.out.println("----------------------------------------------------------------------------");
		if(member.getId()==null) {
			log.info("로그인실패");
		}else {
			System.out.println("로그인성공 , 로그인정보 : "+member.toString());
		}
		System.out.println("----------------------------------------------------------------------------");
		sc.close();
	}

}
