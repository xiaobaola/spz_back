package com.spz.secondHand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.dto.SecondHandItemDto;

import java.util.List;

public interface SecondHandItemService extends IService<SecondHandItem> {

    void changeStatusById(int status, int itemId);

    SecondHandItem getOneById(int itemId);

    List<SecondHandItemDto> getItemDtoByStatus(int status);

    void addItem(SecondHandItem item);

    List<SecondHandItem> getSomeByUserId(int userId);

    void changeItemByItem(SecondHandItem item);

    void deleteByItemId(int itemId);

    void changeItemStatusByItemId(int status, int itemId);

    List<SecondHandItemDto> getItemDtoListBySearchInfo(String info);
}
