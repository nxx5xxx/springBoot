package com.tjoeun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.dto.TmpDto;

@RestController
public class TestController {
//레스트컨트롤러는 데이터를 뷰로 보냄
//	@GetMapping("/")
//	public String home() {
//		return "Spring Boot !!!";
//	}
	@GetMapping("/test1")
	public TmpDto test1() {
		TmpDto t1 = TmpDto.builder()
						  .name("강아지")
						  .height(180)
						  .build();
		return t1;
	}
	@GetMapping("/test2")
	public TmpDto test2() {
		TmpDto t2 = TmpDto.builder()
				.height(180)
				.build();
		return t2;
	}
	

}
