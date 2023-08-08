package com.tjoeun.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.tjoeun.entity.ItemImg;
import com.tjoeun.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

//파이널이 붙은 멤버변수만 생성해주는 생성자
@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemImgRepository itemImgRepository;
	private final FileService fileService;
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
		//파일업로드할때 원래이름 갖고옴
		String oriImgName = itemImgFile.getOriginalFilename();
		
		//유효 아이디를 통해 올라간 이름 갖고옴
		String imgName = "";
		String imgUrl = "";
		// 파일 업로드 : oriImgName 에 내용이 있는지 확인함
		// thymeleaf의 StringUtils를 사용해서 원래 경로 값이 없는지(빈문자열인지) 확인함
		if(!StringUtils.isEmpty(oriImgName)) {
			// ㄴ oriImgName 이 있는경우
			// fileService.uploadFile() 을 호출해서 실제 이미지 이름을 받아옴
			// itemImgLocation : application.properties 파일에 등록한 이미지를 저장할수있디
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			//아래와 같이 설정하면실제 업로드한 이름으로 접근할 수 있음
			imgUrl = "/images/item/" + imgName;
		}
		
		// 이미지를 변경하는 경우 , 상품이미지 정보저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		
		//실제 DB에 저장함
		itemImgRepository.save(itemImg);
	}
	
	//이미지 수정하기
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
		
		//이미지가 있는지 확인하기
		if(!itemImgFile.isEmpty()) {
			//DB에서 해당 itemImgId에 해당하는 이미지 갖고오기
			
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
												.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제 - imgName이 있는 것들만 삭제함
			//itemImgLocation <-- C:/mall/item_images(application.properties)
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				// C:\mall\item_images
				
				fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
			}
			
			// 수정된 이미지 저장하기
			// DB에는 파일이 원래이름(oriImgName)으로 저장된다
			// oriImgName 은 MultipartFile itemImgFile 에 등록되어 있다
			// MultipartFile itemImgFile.getOriginalFileName()
			String oriImgName = itemImgFile.getOriginalFilename();
			
			//project(server)에 저장된 이름에는 UUID가 적용되어 있다
			// ㄴ 외부에서 접근할 때 경로로 사용할 논리적 경로로 지정한다
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			//현재 project 안에서 logical path(논리상의 경로)
			String imgUrl = "/images/item/"+imgName;
			
			// DB에 반영하기
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
		}
	}
}
