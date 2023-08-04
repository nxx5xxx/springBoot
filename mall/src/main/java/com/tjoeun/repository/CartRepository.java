package com.tjoeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.entity.Cart;

//엔티티이름과 아이디역할을 하는 클래스의 멤버변수의 타입을 써줘야한다
//즉 Cart의 ID를 Long으로 지정했으므로 여기엔 롱을 적어줘야한다
public interface CartRepository extends JpaRepository<Cart, Long>{
	
}
