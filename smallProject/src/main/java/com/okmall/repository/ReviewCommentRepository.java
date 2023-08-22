package com.okmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.ReviewComment;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long>{

}
