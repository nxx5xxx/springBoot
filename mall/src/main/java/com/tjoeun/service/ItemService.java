package com.tjoeun.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDto;
import com.tjoeun.entity.Item;
import com.tjoeun.entity.ItemImg;
import com.tjoeun.repository.ItemImgRepository;
import com.tjoeun.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgRepository itemImgRepository;
	private final ItemImgService itemImgService;
	/*
		Item 을 등록하면 item id가 넘어옴
		itemFormDTO 와 MultipartFile을 Generic으로 하는 List를 파라미터로 전달받음
				List<MultipartFile> itemImgFileList
		itemImgFileList : 등록한 이미지 파일들을 list로 갖고온다
		itemFormDTO : front단에서 입력한 data를 저장해서 server단으로 가져옴
	 */
	public Long saveItem(ItemFormDto itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
		//상품등록
		//itemFormDTO.createItem() 에서 mapper를 사용해서
		//dto 를 entity로 바꾼 Item(entity) 객체를 받아온다
		Item item = itemFormDTO.createItem();
		
		/*
		 	DB에 저장함 : 이미지번호(id)가 생김
		 	Item item 변수에 할당하지 않아도
		 	consistence 영역에 저장되어서 값을 확인할 수 있다
		 	return item.getId() : 업로드한상품번호(id) <-- 행의 Item item과 같은 item
		 */
		itemRepository.save(item);
		
		//이미지 등록 : 저장한 이미지 개수만큼 등록함
		for(int i=0;i<itemImgFileList.size();i++) {
			//ItemImg는 Entity임
			//item image등록을 위해서 ItemImg 객체를 생성함
			ItemImg itemImg = new ItemImg();
			
			/*
			 persistence 영역에 있는 item객체를 setItem으로 저장함
			 id값을 사용하게 도니다 <-- foreign key 가 설정됨
			 몇 번째 등록인지를 image에 설정함;
			 첫 번째 이면 아래 if문에서 대표 이미지로 설정
			 */
			itemImg.setItem(item);
			//첫번째 이미지를 대표 이미지로 설정함
			if(i==0) {
				itemImg.setRepImgYn("Y");
			}else {
				itemImg.setRepImgYn("N");
			}
			//이미지를 DB에 저장함
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		return item.getId();
	}
}
