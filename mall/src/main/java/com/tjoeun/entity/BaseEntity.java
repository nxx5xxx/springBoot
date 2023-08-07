package com.tjoeun.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EntityListeners(value= {AuditingEntityListener.class})
@MappedSuperclass
public abstract class BaseEntity {
	
	//처음 작성한 사람
	@CreatedBy
	@Column(updatable=false)
	private String createdBy;
	
	//마지막으로 수정한사람
	@LastModifiedBy
	private String modifiedBy;
	
	//생성될때 , 업데이트 불가
	@CreatedDate
	@Column(updatable=false)
	private LocalDateTime regTime;
	
	@LastModifiedDate
	private LocalDateTime updateTime;
}
