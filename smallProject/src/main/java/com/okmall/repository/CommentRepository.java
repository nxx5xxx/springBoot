package com.okmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	

}
