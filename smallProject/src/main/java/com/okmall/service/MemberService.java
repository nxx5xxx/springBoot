package com.okmall.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.okmall.entity.Member;
import com.okmall.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@RequiredArgsConstructor 생성자를 사용한주입
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member){
		validateDuplicate(member);
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}
	private void validateDuplicate(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
//		Optional<Member> foundMember = memberRepository.findByEmail(member.getEmail());
//		if(foundMember.isPresent()) {
//			throw new IllegalStateException("이미 가입하신 회원입니다");
//		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }
        log.info(">>>>>>>>>>>>>>> loginUserByUsername" + member);
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
//		Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		
//		
//		return 	User.builder()
//					.username(member.getEmail())
//					.password(member.getPassword())
//					.roles(member.getRole().toString())
//					.build();
	}
	
	
}
