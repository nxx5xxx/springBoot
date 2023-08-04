package com.tjoeun.test;

import java.util.Optional;

public class OptionalTest {
	public static void main(String[] args) {
		
		String str1 ="spring";
		
		Optional<String> opt1 = Optional.of(str1);
		//str1을 옵셔널클래스를 바꿧다
		// 널포인터 익셉션에러를 잡아줌
		//of는 옵셔널에 있는 값을 꺼내오는 역할을 한다
		
		Optional<String> opt2 = Optional.of("스프링");
		
		//널포인터 익센셥에러발생
		//Optional<String> opt3 = Optional.of(null);
		
		//널값이 가능한메소드
		Optional<String> opt4 = Optional.ofNullable(null);
		
		Optional<String> opt5 = null;
		
		//이것또한 일종의 null을 의미
		Optional<String> opt6 = Optional.<String>empty();
		
		Optional<String> opt7 = Optional.empty();
		
		
		System.out.println("opt1 : " + opt1);
		System.out.println("opt2 : " + opt2);
		//System.out.println("opt3 : " + opt3);
		System.out.println("opt4 : " + opt4);
		System.out.println("opt5 : " + opt5);
		System.out.println("opt6 : " + opt6);
		System.out.println("opt7 : " + opt7);
		
		/////////////////////////////////////////////		
		//길이가 0인배열 <-- item엘리먼트가 하나도 없는 배열
		Object[] objArr1 = new Object[0];
		
		// 할수는 있지만 권장하지 않는방법
		Object[] objArr2 = null;
		/////////////////////////////////////////////
		
		String str2 = "";
		//권장하지 않는방법
		String str3 = null;
		
		/////////////////////////////////////////////
		
		Optional<String> opt8 = Optional.of("오피티8");
		String str4 = opt8.get();
		
		System.out.println("str4 : "+str4);
		System.out.println("opt8 : "+opt8);
		
		String str5 = opt8.orElse("");//널값일경우 ""를 출력
		System.out.println("str5 : "+str5);
		
		
		Optional<String> opt9 = Optional.ofNullable(null);		
		//널포인트 익셉션발생
		//Optional에 null 이 들어있으면
		//get()으로 확인하면 예외발생
		//String str6 = opt9.get();
		String str6 = opt9.orElse("널일때 반환하는 문자열");
		System.out.println("널임 str6 : "+str6);
		
		
		opt9 = Optional.ofNullable("opt9가 널 아니지롱");
		str6 = opt9.orElse("str6널일때 반환하는 문자열");
		System.out.println("널이아님 str6 : "+str6);
		
		/////////////////////////////////////////////
		
		//orElseGet
		//리터럴데이터
		//리터럴 데이터는 new String("테스트")
		//orElseGet은 람다식을 넣어줘야함
		//NullorElseGet 을 줄인것인데 Null값이면 람다식에 있는 값을 갖고온다는 뜻이다
		String str7 = opt9.orElseGet(() -> new String("테스트"));
		System.out.println("str7 : "+str7);
		opt9 = Optional.ofNullable(null);
		str7 = opt9.orElseGet(() -> new String("테스트"));
		System.out.println("str7 : "+str7);
		//즉 orElse는 null값일때 뒤에있는것으로 대체가 되는것이고
		
		// orElseThrow() : null 이 있는경우 지정한 예외를 발생시킨다
		opt9 = Optional.ofNullable("스프링부트");
		String str10 = opt9.orElseThrow(()-> new NullPointerException());
		System.out.println("str10 : "+str10);
		//(()-> new NullPointerException()); 과 같은것
		str10 = opt9.orElseThrow(NullPointerException::new);
		
		String str11 = "강아지";
		//Optional.ofNullable(str11) 이 null 이 아닌 경우에만 출력하기
		if(Optional.ofNullable(str11).isPresent()) {
			System.out.println("str11 : " +str11);
		}
		
		str11 = null;
		//Optional.ofNullable(str11) 이 null 이 아닌 경우에만 출력하기
		if(Optional.ofNullable(str11).isPresent()) {
			System.out.println("str11 : " +str11);
		}
		//이렇게 쓰면 코드가 간단해진다는 장점이 있다
		System.out.println("--------------------------------");
		str11 = "호룔";
		Optional.ofNullable(str11).ifPresent(System.out::println);
		
		str11 = null;
		Optional.ofNullable(str11).ifPresent(System.out::println);
		System.out.println("--------------------------------");
		//문자열을 리턴하는 메소드
		//name은 정의한적없지만 .length()메소드를 추론해서 Stringㅋㄹ래스이라는것을 알아서 찾아준다
		Optional<String> optStr1 = Optional.of("하나");
		Optional<Integer> optInt1 = optStr1.map(name -> name.length());
		Optional<Integer> optInt2 = optStr1.map(String::length);
		
		System.out.println(".get()을 안넣었을시");
		System.out.println("optStr1 : "+optStr1.get());
		System.out.println("optInt1 : "+optInt1.get());
		System.out.println("optInt2 : "+optInt2.get());
		System.out.println("optStr1 : "+optStr1);
		System.out.println("optInt1 : "+optInt1);
		System.out.println("optInt2 : "+optInt2);
		
		System.out.println("---------------------------------");
		Optional<Integer> number1 = Optional.of("1234")
				.filter(num -> num.length() > 0)
				.map(Integer::parseInt);
		int n1 = number1.get();
		//위에거와 같은것
		//여기서 filter는 길이가 0즉 빈문자열이 아니라면 이라는것 if문 같은것이다
		int number1_2 = Optional.of("1234")
				.filter(num -> num.length() > 0)
				.map(Integer::parseInt)
				.get();
		
		int number2 = Optional.of("")
				.filter(num -> num.length() > 0)
				.map(Integer::parseInt)
				.orElse(-1);
		
		System.out.println(number1);
		System.out.println(number1_2);
		System.out.println(number2);
		System.out.println("---------------------------------");
		//더블콜론과 람다식으로 간단하게 쓸경우
		Optional.of("987")
				.map(Integer::parseInt)
				.ifPresent(System.out::println);
		Optional.of("12345")
				.map(Integer::parseInt)
				.ifPresent(num -> System.out.println("num : "+ num));
		
		//파스인트를 못함
		/* Optional.of("")
		.map(Integer::parseInt)
		.ifPresent(num -> System.out.println("num : "+ num));*/
		
		
	}
}
