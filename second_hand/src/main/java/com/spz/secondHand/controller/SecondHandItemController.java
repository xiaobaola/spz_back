package com.spz.secondHand.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.common.Res;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.dto.SecondHandItemDto;
import com.spz.secondHand.entity.wrapper.SecondHandWrapper;
import com.spz.personal_extend.entity.User;
import com.spz.secondHand.service.SecondHandItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/item")
@Slf4j
@Tag(name = "二手物品模块")
@RequiredArgsConstructor
public class SecondHandItemController {

    private final SecondHandItemService itemService;

    @Cacheable(value = "itemList",key = "'status_2'")
    @GetMapping("/list")
    public Res<List<SecondHandItemDto>> listStatus2(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return Res.success(itemService.getItemDtoByStatus(2));
    }
    @Cacheable(value = "itemList",key = "'status_1'")
    @GetMapping("/list/manager")
    public Res<List<SecondHandItemDto>> listStatus1(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return Res.success(itemService.getItemDtoByStatus(1));
    }
    @GetMapping
    public Res<SecondHandItem> getOneById(@RequestParam int itemId){
        // 根据itemId获取一个二手物品信息
        log.info("获取一个二手物品信息，参数{}",itemId);
        return Res.success(itemService.getOneById(itemId));
    }
    @PostMapping
    public Res<String> sellerUploadItem(@RequestBody SecondHandWrapper wrapper) {
//        log.info("卖家发布二手物品，物品wrapper{}",wrapper);
        // 对传输的数据进行传递
        SecondHandItem item = new SecondHandItem();
        item.setImage(wrapper.getImage());
        item.setPrice(wrapper.getPrice());
        item.setInformation(wrapper.getInformation());
        int userId = wrapper.getUserId();
//        userId = User.getUserIdByThread(userId);
        item.setUserId(userId);
        log.info("卖家发布二手物品，物品{}",item);
        itemService.addItem(item);
        return Res.success("发布成功");
    }

    @PutMapping
    public Res<String> modifyItem(@RequestBody SecondHandItem item){
        log.info("卖家修改物品，参数{}",item);
        itemService.changeItemByItem(item);
        return Res.success("修改物品信息成功");
    }

    @Cacheable(value = "itemListSeller",key = "'userId'+#userId")
    @GetMapping("/seller")
    public Res<List<SecondHandItem>> itemListSeller(@RequestParam int userId, HttpServletRequest request){
        userId = User.getUserIdBySession(userId,request);
        log.info("卖家获取自己的发布，参数sellerId:{}",userId);
        return Res.success(itemService.getSomeByUserId(userId));
    }

    @PutMapping("/seller/up")
    public Res<String> sellerApplyItem(@RequestBody SecondHandItem item){
        log.info("卖家申请审核，参数{}",item);
        int status = 1; // 1:待审核 2:发布中 3:下架
        itemService.changeItemStatusByItemId(status,item.getId());
        return Res.success("审核中");
    }
    @PutMapping("/seller/down")
    public Res<String> sellerOffItem(@RequestBody SecondHandItem item){
        log.info("卖家下架物品，参数{}",item);
        int status = 3; // 1:待审核 2:发布中 3:下架
        itemService.changeItemStatusByItemId(status,item.getId());
        return Res.success("已下架");
    }

    @CacheEvict(value = "getSomeByUserId",allEntries = true)
    @DeleteMapping
    public Res<String> sellerDeleteItem(@RequestParam int itemId){
        // 卖家删除物品 物品状态更改，数据库删除数据
        // 直接删除
        log.info("卖家删除物品，参数{}",itemId);
        itemService.deleteByItemId(itemId);
        LambdaQueryWrapper<SecondHandItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItem::getId,itemId);
//        itemService.remove(queryWrapper);
        return Res.success("删除成功");
    }

    @CacheEvict(value = "itemList",allEntries = true)
    @PostMapping("/manager")
    public Res<String> managerOffShelf(@RequestBody SecondHandWrapper wrapper) {
        // 物品状态设置为下架状态 3
        int itemId = wrapper.getItemId();
        int status = 3;
        itemService.changeItemStatusByItemId(status,itemId);
        return Res.success("物品下架成功");
    }

    @GetMapping("/search")
    public Res<List<SecondHandItemDto>> getItemDtoListBySearchInfo(@RequestParam String info) {
        // 对于复杂的操作可以考虑使用 redis 缓存减少频繁的操作 搜索功能除外，因为搜索信息频繁改动
        log.info("搜索物品，参数{}",info);
        // 运用mybatisPlus技术可以大大地提升开发效率
        return Res.success(itemService.getItemDtoListBySearchInfo(info));
    }
}

