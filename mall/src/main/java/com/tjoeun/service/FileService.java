package com.tjoeun.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
		UUID uuid = UUID.randomUUID();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension;
		//실제 물리적인 server 컴퓨터 내의 경로
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		//입출력작업할때는 바로바로 없애줘야한다
		fos.close();
		return savedFileName;
	}
	
	//이미지 삭제하기
	public void deleteFile(String filePath){
		
		File deleteFile = new File(filePath);
		//deleteFile.exists()파일있으면 true없으면 false
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("----------파일 삭제 완료--------");
		}else {
			log.info("----------삭제할 파일이 없음--------");
			
		}
	}
}
