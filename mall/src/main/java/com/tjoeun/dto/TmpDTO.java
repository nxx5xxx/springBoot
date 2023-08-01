package com.tjoeun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Data말고 수동으로 하나씩 넣는법
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmpDTO {
	//빌더패턴은
	//필요한 클래스만 선택해서 값을 넣을 수 있는것
	private String name;
	private int height;
}
