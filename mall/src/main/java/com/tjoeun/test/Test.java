package com.tjoeun.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public class Test {

	public static void main(String[] args) {
		// **1 == Student s1 = new Student("강아지",179)
		// 위에방식이 더 쉬워보이지만 일일히 패턴에 따른 생성자를 만들어줘야한다
		Student s1 = Student.builder()
							.name("강아지")
							.height(179)
							.build();
		//ToString을 안하면 해쉬값(해쉬코드)으로 나온다 - 주소값이라 생각하면된다
		System.out.println(s1);
		//빌더는 이런식으로 객체를 생성할수 있는 역할을 한다
		Student s2 = Student.builder()
							.name("고양이")
							.build();
		
		
	}
}

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class Student{
	private String name;
	private int age;
	private int height;
	private int weight;
	//생성자의 매개변수에 반드시 멤버변수만 와야하는것이 아니다
	
	
	//기본으로 제공해주는두가지는 이것이고
	//public Student() {} == NoArgsConstructor
	//public Student(String name,int age, int height, int weight) {}
	// == AllArgsConstructor
	
	// Builder 라는 패턴을 사용하면 생성자를 일일히 지정안해줘도 가능하다 
	// 맨위에서 설명 - **1
//	public Student(String name,int age,int height) {
//		this.name = name;
//		this.age = age;
//		this.height = height;
//	}
	
	//투스트링을 오버라이드하면 해쉬코드가 아닌 이름과 키가 나오게되는것
	@Override
	public String toString() {
		return this.name + "-" + this.age + "-" + this.height + "-" + this.weight;
	}
	
}
