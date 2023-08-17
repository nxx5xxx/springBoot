package com.okmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>{
	
	//옵셔널을 안할때
	Member findByEmail(String email);
	
	//옵셔널로할때
	//Optional<Member> findByEmail(String email);
}
