package com.okmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okmall.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}