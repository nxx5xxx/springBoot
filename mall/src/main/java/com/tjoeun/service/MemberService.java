package com.tjoeun.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjoeun.entity.Member;
import com.tjoeun.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@RequiredArgsConstructor 생성자를 사용한주입
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepository;
	
	/*
	public Member findByEmail(String email){
		Member member = memberRepository.findByEmail(email);
		return member;
	}
	*/
	public Member saveMember(Member member){
		//가입하려는 회원과 같은 회원이 있는지 검증하는 메소드 호출하기
		validateDuplicate(member);
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}
	
	// 가입하려는 회원과 같은 회원이 검증하는 메소드
	private void validateDuplicate(Member member) {
		Optional<Member> foundMember = memberRepository.findByEmail(member.getEmail());
		if(foundMember.isPresent()) {
			throw new IllegalStateException("이미 가입하신 회원입니다");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//email로 회원 조회해서 로그인하기

		//옵셔널로 하지않을때
		//Member member = memberRepository.findByEmail(email);
		/*
		  if(member==null) {
			throw new UsernameNotFoundException("해당 이메일로 가입한 회원이 없습니다"+email);
		}
		  
		 */
		//옵셔널로 할때
		//멤버레포지토리도 옵셔널로받고
		Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		/*
		if(!member.isPresent()) {
			throw new UsernameNotFoundException("해당 이메일로 가입한 회원이 없습니다"+email);
		}
		*/
		
		log.info(">>>>>>>>>>>>>>> loginUserByUsername" + member);
		
		return 	User.builder()
					.username(member.getEmail())
					.password(member.getPassword())
					.roles(member.getRole().toString())
					.build();
	}
	
	
}
