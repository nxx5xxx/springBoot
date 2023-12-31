package com.okmall.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.okmall.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.member.email = :email"
    )
    Long countOrder(@Param("email") String email);
    
    @Query("select o from Order o " +
            "where o.member.email = :email " +
    		"and o.orderStatus = 'ORDER' "+
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email);
    

}