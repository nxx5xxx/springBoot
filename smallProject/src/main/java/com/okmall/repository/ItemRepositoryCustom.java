package com.okmall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.okmall.dto.ItemSearchDto;
import com.okmall.dto.MainItemDto;
import com.okmall.entity.Item;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
