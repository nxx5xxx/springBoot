package com.tjoeun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.dto.TmpDTO;

@RestController
public class TestController {
//레스트컨트롤러는 데이터를 뷰로 보냄
	@GetMapping("/")
	public String home() {
		return "Spring Boot !!!";
	}
	@GetMapping("/test1")
	public TmpDTO test1() {
		TmpDTO t1 = TmpDTO.builder()
						  .name("강아지")
						  .height(180)
						  .build();
		return t1;
	}
	@GetMapping("/test2")
	public TmpDTO test2() {
		TmpDTO t2 = TmpDTO.builder()
				.height(180)
				.build();
		return t2;
	}
}
