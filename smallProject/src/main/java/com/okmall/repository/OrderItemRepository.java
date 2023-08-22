package com.okmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okmall.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	@Query("select o from OrderItem o "
			+ "where o.order.id = :itemId")
	OrderItem findByOrderId(Long itemId);
}