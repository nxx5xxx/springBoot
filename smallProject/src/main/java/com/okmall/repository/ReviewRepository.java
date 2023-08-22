package com.okmall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.okmall.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
	@Query("select  "
			+ "distinct r  "
			+ "from Review r  "
			+ "left outer join Member m1 on r.member = m1 "
			+ "left outer join Item i on r.item = i "
			+ "where r.title like %:keyword% "
			+ "or m1.name like %:keyword% "
			+ "or r.content like %:keyword%"
			+ "or i.itemNm like %:keyword%")
		Page<Review> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
