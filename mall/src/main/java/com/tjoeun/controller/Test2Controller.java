package com.tjoeun.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.ItemDTO;
import com.tjoeun.test.Person;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/test")
public class Test2Controller {
	
	@GetMapping("/t1")
	public void t1(Person person, Model model) {
		
		person.setName("강아지");
		person.setHeight(188);
		
		model.addAttribute("text", "String Boot 2.7.14");
	}
	
	@GetMapping("/t2")
	public void t2(ItemDTO itemDto) {
		itemDto.setItemDetail("상세설명-");
		itemDto.setItemNm("상품-");
		itemDto.setPrice(30000);
		itemDto.setRegTime(LocalDateTime.now());
	}
	
	@GetMapping({"/t3" , "/t4"})
	public void t3(ItemDTO itemDto,Model model) {
		
		List<ItemDTO> itemList = new ArrayList<>();
			for(int i=0;i<10;i++) {
			itemDto.setItemDetail("상세설명-"+(i+1));
			itemDto.setItemNm("상품-"+(i+1));
			itemDto.setPrice(30000);
			itemDto.setRegTime(LocalDateTime.now());
			itemList.add(itemDto);
		}
		
			model.addAttribute("itemList",itemList);
	}
	
	@GetMapping("/t5")
	public void t5(String name,String height,Model model) {
		//void대신 String넣고 return "test/t5"; 랑 같다
		log.info(name,height);
		model.addAttribute("name", name);
		model.addAttribute("height", height);
	}
	
	@GetMapping({"/content1","/content2"})
	public void content() {
		
	}
	
}

