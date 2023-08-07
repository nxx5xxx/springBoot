package com.tjoeun.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDto;
import com.tjoeun.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	
	@GetMapping("/admin/item/new")
	public String itemForm(ItemFormDto itemFormDTO) {
		return "item/itemForm";
	}
	@PostMapping("/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDTO,BindingResult result,Model model,
			@RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList) {
		if(result.hasErrors()) {
			return "item/itemForm";
		}
		// 상품 이미지를 선택 하지 않고 상품저장을 누른경우
		// 상품 이미지는 최소한 하나는 올려야 되도록 한다
		if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 반드시 업로드 해야합니다");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDTO, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록중 오류가 발생하였습니다");
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
}
