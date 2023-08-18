package com.okmall.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.okmall.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long>{
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	/*
	select 
		distinct q 
		from Question q 
		left outer join Users u1 on q.users = u1
		left outer join Answer a on a.question = q
		left outer join Users u2 on a.users = u2
		where q.subject like %:keyword%
		   or q.subject like %:keyword%
		   or u1.username like %:keyword%
		   or a.content like %:keyword%
	
	*/
	@Query("select  "
		+ "distinct q  "
		+ "from Question q  "
		+ "left outer join Member m1 on q.member = m1 "
		+ "left outer join Answer a on a.question = q "
		+ "left outer join Member m2 on a.member = m2 "
		+ "where q.subject like %:keyword% "
		+ "or q.subject like %:keyword% "
		+ "or m1.name like %:keyword% "
		+ "or a.content like %:keyword%")
	Page<Question> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
