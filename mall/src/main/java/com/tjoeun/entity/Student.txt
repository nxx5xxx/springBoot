package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Entity 클래스 이름과 table이름을 다르게 하는경우
//테이블 이름을 바꿔서 하고싶을경우
//@Table(name="colleaugue")
@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//오토인크리먼트
	private Long id;
	
	//컬럼이름을 바꾸고싶을때 nullable을 false로 할 시 notnull
	// length = 길이 제한
	@Column(name="name", nullable=false, length=30)
	private String myName;
	private int myHeight;
	//이렇게하면 db에서는 스네이크케이스로 생성된다 (my_name , my_height)
}
