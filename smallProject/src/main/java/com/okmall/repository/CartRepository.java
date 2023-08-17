package com.okmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}