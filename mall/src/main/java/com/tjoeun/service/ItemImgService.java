package com.tjoeun.service;

import java.io.IOException;

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
	
	@Value("${itmImgLocation}")
	private String itmImgLocation;
	
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
		if(!StringUtils.isEmpty(imgUrl)) {
			// ㄴ oriImgName 이 있는경우
			// fileService.uploadFile() 을 호출해서 실제 이미지 이름을 받아옴
			// itemImgLocation : application.properties 파일에 등록한 이미지를 저장할수있디
			imgName = fileService.uploadFile(itmImgLocation, oriImgName, itemImgFile.getBytes());
			//아래와 같이 설정하면실제 업로드한 이름으로 접근할 수 있음
			imgUrl = "/images/item/" + imgName;
		}
		
		// 이미지를 변경하는 경우 , 상품이미지 정보저장
		itemImg.updateImg(oriImgName, imgName, imgUrl);
		
		//실제 DB에 저장함
		itemImgRepository.save(itemImg);
	}
}
