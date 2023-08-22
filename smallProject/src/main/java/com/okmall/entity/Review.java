package com.okmall.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.okmall.dto.ReviewFormDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="review")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Review extends BaseEntity{
	
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	//글쓴이
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;

	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	//추천
	@ManyToMany
	private Set<Member> voter;
	
	@OneToMany(mappedBy  = "review", cascade = CascadeType.ALL,
			orphanRemoval = true,fetch = FetchType.LAZY)
	private List<ReviewComment> reviewCommentList = new ArrayList<>();
	
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL,
			orphanRemoval = true , fetch = FetchType.LAZY)
	private List<ReviewImg> reviewImgList = new ArrayList<>();
	
    public void updateReview(ReviewFormDto reviewFormDto){
        this.title = reviewFormDto.getTitle();
        this.content = reviewFormDto.getContent();
    }
}
