package com.spz.service;

import com.spz.entity.secondhand.SecondHandItem;
import com.spz.entity.secondhand.SecondHandItemDto;

import java.util.List;

public interface SecondHandItemService {
    List<SecondHandItem> getSomeByStatus(int status);

    void changeStatusById(int status, int itemId);

    int getUserIdById(int itemId);

    SecondHandItem getOneById(int itemId);

    List<SecondHandItemDto> getItemDtoByStatus(int status);
}
