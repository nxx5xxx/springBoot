package com.tjoeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.entity.Member;

public interface MemberRepositoryTest extends JpaRepository<Member,Long> {

	Member findByEmail(String email);

}
