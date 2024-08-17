package com.spz.secondHand.service;

import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.dto.SecondHandItemDto;

import java.util.List;

public interface SecondHandItemService {
    List<SecondHandItem> getSomeByStatus(int status);

    void changeStatusById(int status, int itemId);

    int getUserIdById(int itemId);

    SecondHandItem getOneById(int itemId);

    List<SecondHandItemDto> getItemDtoByStatus(int status);

    void createItem(SecondHandItem item);

    List<SecondHandItem> getSomeByUserId(int userId);

    void changeItemByItem(SecondHandItem item);

    void deleteByItemId(int itemId);

    void changeItemStatusByItemId(int status, int itemId);
}
