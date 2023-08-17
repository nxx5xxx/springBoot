package com.okmall.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDto {

    @NotNull(message = "상품 아이디는 반드시 입력해 주세요")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다")
    @Max(value = 999, message = "최대 주문 수량은 999개 입니다")
    private int count;

}