package com.tjoeun.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDto;
import com.tjoeun.dto.ItemSearchDto;
import com.tjoeun.entity.Item;
import com.tjoeun.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	
	
	//상품등록
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
	
	//상품수정
	@GetMapping("/admin/item/{itemId}")
	public String itemUpdate(@PathVariable("itemId") Long itemId, Model model) {
	//	 PathVariable 은 매핑속에있는 {~}를 받아온다
		try {
		ItemFormDto itemFormDto = itemService.getItemDetail(itemId);
		model.addAttribute("itemFormDto", itemFormDto);
		}catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "등록되지 않은 상품입니다");
			model.addAttribute("itemFormDto", new ItemFormDto());
			return "item/itemForm";
		}
		return "item/itemForm";
	}
	
	@PostMapping("/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDTO,BindingResult result,Model model,
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
			itemService.updateItem(itemFormDTO, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록중 오류가 발생하였습니다");
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@GetMapping({"/admin/items","/admin/items/{page}" })
	public String itemList(ItemSearchDto itemSearchDto,
							@PathVariable("page") Optional<Integer> page,Model model) {
		//page.isParent? page.get() :
		//Pageable page 정보를 담고 있는 인터페이스-스프링에서는 임플리먼츠를 자동으로 객체로 생성해준다
		// 5개까지 보여준다고 5 적은것

			Pageable pageable = PageRequest.of( page.isPresent()? page.get():0, 5);

		//Pageable pageable = PageRequest.of( 0, 5);
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		
		
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		
		// 페이지 최대 사이즈
		model.addAttribute("maxPage", 5);
		return "item/itemList";
	}
	
}
