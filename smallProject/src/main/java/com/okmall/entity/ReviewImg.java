package com.okmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="review_img")
@Getter @Setter
public class ReviewImg extends BaseEntity{
	
	@Id
	@Column(name="review_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reviewImgName;
	
	private String reviewOriImgName;
	
	private String reviewImgUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;
	
    public void updateItemImg(String reviewOriImgName, String reviewImgName, String reviewImgUrl){
        this.reviewOriImgName = reviewOriImgName;
        this.reviewImgName = reviewImgName;
        this.reviewImgUrl = reviewImgUrl;
    }
	  
	
}
