package com.tjoeun.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDto;
import com.tjoeun.dto.ItemImgDto;
import com.tjoeun.dto.ItemSearchDto;
import com.tjoeun.entity.Item;
import com.tjoeun.entity.ItemImg;
import com.tjoeun.repository.ItemCustomRepository;
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
	
	//아이템 저장하기
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
	
	//상품 수정하기
	// 상품 수정하려고 이미 저장되어있는 item을 db에서 갖고와서 ItemFormDto에 담아 itemFormDto를 반환함
	public ItemFormDto getItemDetail(Long itemId) {
		
		//Db에서 이미지 갖고오기 : DB에서 갖고오므로 Entity인 ItemImg list로 한다
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		
		// Entity를 DTO로 옮기고 이를 mapping 하는 image list를 만든다
		// DB에서 이미지를 갖고와서 View에 보여줄려고함
		//	ㄴ 수정해야 하므로 원래의 내용을 화면에 보여준다
		List<ItemImgDto> ItemImgDtoList = new ArrayList<>();
		
		for(ItemImg itemImg : itemImgList) {
			// ItemmImgDto.of : Entity(itemImg)를 받아 DTO(ItemImgDto) 로변환하는 mapper
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			ItemImgDtoList.add(itemImgDto);
		}
		// DB에서 item테이블에 있는 값을 꺼내온다
//		Optional<Item> optionalItem = itemRepository.findById(itemId);
//		Item item = optionalItem.orElseThrow(EntityNotFoundException::new);
		//같은것
		Item item = itemRepository.findById(itemId)
									.orElseThrow(EntityNotFoundException::new);
		
		//Item(Entity) --> ItemFormDto 
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		itemFormDto.setItemImgDtoList(ItemImgDtoList);
		return itemFormDto;
	}
	
	//위에 데이터를 받아 수정하는작업
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
		// 상품업데이트(수정)
		//수정 대상 상품(item) 을 DB에서 갖고오기 : Item Entity
		Item item = itemRepository.findById(itemFormDto.getId())
									.orElseThrow(EntityNotFoundException::new); // orElseThrow를넣어서 옵셔널 예외가 발생하지않을시 Item반환
		//파라미터로 전달되어 들어온 itemFormDTo객체를 argument로 넣음
		item.updateItem(itemFormDto);
		
		//상품 이미지 업데이트(수정)
		// 상품 이미지 번호(id)들을 다 갖고옴
		List<Long> itemImgIds = itemFormDto.getItemImgIds();
		
		// 상품 이미지 업데이트 하기 : itemImgId , itemImgFileList
		for(int i=0;i<itemImgFileList.size();i++) {
			// ItemImgService에 있는 updateItemImg() 메소드 호출하기
			// 상품 이미지 번호(이미지 아이디번호) 와 실제파일을 argument 로 넣어줌
			// itemImgId, itemImgFileList <- 이 두개의 값이 필요하다
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		//정보를 수정한 상품이 몇번쨰 상품인지 반환함
		// 이후에는컨트롤러로 가서 수정작업
		return item.getId();
	}
	
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,Pageable pageable){
		
		//이부분 다시봐야함
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
}
