package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.SecondHandItem;
import com.spz.entity.dto.SecondHandItemDto;
import com.spz.entity.wrapper.SecondHandWrapper;
import com.spz.entity.User;
import com.spz.service.SecondHandItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/item")
@Slf4j
public class SecondHandItemController {

    private SecondHandItemService itemService;

    @Autowired
    public void setItemService(SecondHandItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public Res<List<SecondHandItemDto>> listStatus2(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return Res.success(itemService.getItemDtoByStatus(2));
    }
    @GetMapping("/list/manager")
    public Res<List<SecondHandItemDto>> listStatus1(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return Res.success(itemService.getItemDtoByStatus(1));
    }
    @PostMapping()
    public Res<String> sellerUploadItem(@RequestBody SecondHandWrapper wrapper) {
//        log.info("卖家发布二手物品，物品wrapper{}",wrapper);
        // 对传输的数据进行传递
        SecondHandItem item = new SecondHandItem();
        item.setImage(wrapper.getImage());
        item.setPrice(wrapper.getPrice());
        item.setInformation(wrapper.getInformation());
        item.setUserId(wrapper.getUserId());
        log.info("卖家发布二手物品，物品{}",item);
        itemService.addItem(item);
        return Res.success("发布成功");
    }
    @GetMapping("/seller")
    public Res<List<SecondHandItem>> itemListSeller(@RequestParam int userId, HttpServletRequest request){
        userId = User.getUserIdBySession(userId,request);
        log.info("卖家获取自己的发布，参数sellerId:{}",userId);
        return Res.success(itemService.getSomeByUserId(userId));
    }

    @PutMapping
    public Res<String> modifyItem(@RequestBody SecondHandItem item){
        log.info("卖家修改物品，参数{}",item);
        itemService.changeItemByItem(item);
        return Res.success("修改物品信息成功");
    }

    @DeleteMapping
    public Res<String> sellerDeleteItem(@RequestParam int itemId){
        // 卖家删除物品 物品状态更改，数据库删除数据
        // 直接删除
        log.info("卖家删除物品，参数{}",itemId);
        itemService.deleteByItemId(itemId);
        return Res.success("删除成功");
    }

    @PostMapping("/manager")
    public Res<String> managerOffShelf(@RequestBody SecondHandWrapper wrapper) {
        // 物品状态设置为下架状态 3
        int itemId = wrapper.getItemId();
        int status = 3;
        itemService.changeItemStatusByItemId(status,itemId);
        return Res.success("物品下架成功");
    }
}
