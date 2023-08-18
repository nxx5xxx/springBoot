package com.okmall.service;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.okmall.entity.ReviewImg;
import com.okmall.repository.ReviewImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {
	@Value("${reviewImgLocation}")
	private String reviewImgLocation;
	
    private final FileService fileService;
    
    private final ReviewImgRepository reviewImgRepository;
    
    public void saveReviewImg(ReviewImg reviewImg , MultipartFile reviewImgFile)throws Exception {
    	String oriImgName = reviewImgFile.getOriginalFilename();
    	String imgName = "";
    	String imgUrl = "";
    	
    	if(!StringUtils.isEmpty(oriImgName)) {
    		imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
    		imgUrl = "/images/review/" + imgName;
    	}
    	reviewImg.updateItemImg(oriImgName, imgName, imgUrl);
    	reviewImgRepository.save(reviewImg);

    }
    
    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception{
    	if(!reviewImgFile.isEmpty()) {
    		ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId)
    													 .orElseThrow(EntityNotFoundException::new);
    		if(!StringUtils.isEmpty(savedReviewImg.getReviewImgName())) {
    			fileService.deleteFile(reviewImgLocation+"/"+savedReviewImg.getReviewImgName());
    		}
    		String oriImgName = reviewImgFile.getOriginalFilename();
    		String imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
    		String imgUrl = "/images/review/" + imgName;
    		savedReviewImg.updateItemImg(oriImgName, imgName, imgUrl);
    	}
    }

}
