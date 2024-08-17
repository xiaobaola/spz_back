package com.spz.service;

import com.spz.common.Res;
import com.spz.entity.secondhand.SecondHandItem;
import com.spz.entity.dto.SecondHandItemDto;

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
