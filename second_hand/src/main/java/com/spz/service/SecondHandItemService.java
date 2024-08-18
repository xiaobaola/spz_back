package com.spz.service;

import com.spz.entity.SecondHandItem;
import com.spz.entity.dto.SecondHandItemDto;

import java.util.List;

public interface SecondHandItemService {

    void changeStatusById(int status, int itemId);

    SecondHandItem getOneById(int itemId);

    List<SecondHandItemDto> getItemDtoByStatus(int status);

    void addItem(SecondHandItem item);

    List<SecondHandItem> getSomeByUserId(int userId);

    void changeItemByItem(SecondHandItem item);

    void deleteByItemId(int itemId);

    void changeItemStatusByItemId(int status, int itemId);
}
