package com.spz.secondHand.controller;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.common.Res;
import com.spz.personal.service.UserService;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.SecondHandItemImage;
import com.spz.secondHand.entity.SecondHandItemReject;
import com.spz.secondHand.entity.SecondHandItemTag;
import com.spz.secondHand.entity.dto.SecondHandItemDto;
import com.spz.secondHand.entity.wrapper.SecondHandWrapper;
import com.spz.personal.entity.User;
import com.spz.secondHand.service.ISecondHandItemTagService;
import com.spz.secondHand.service.SecondHandItemImageService;
import com.spz.secondHand.service.SecondHandItemRejectService;
import com.spz.secondHand.service.SecondHandItemService;
import com.spz.tag.entity.TagTagGroup;
import com.spz.tag.entity.dto.TagGroupDto;
import com.spz.tag.entity.dto.TagTagGroupDto;
import com.spz.tag.service.ITagService;
import com.spz.tag.service.ITagTagGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/item")
@Slf4j
@Tag(name = "二手物品模块")
@RequiredArgsConstructor
public class SecondHandItemController {

    private final SecondHandItemService itemService;
    private final SecondHandItemRejectService rejectService;
    private final SecondHandItemImageService itemImageService;
    private final UserService userService;
    private final ISecondHandItemTagService itemTagService;
    private final ITagTagGroupService tagTagGroupService;

    @Cacheable(value = "itemList",key = "'status_2'")
    @GetMapping("/list")
    public Res<List<SecondHandItemDto>> listStatus2(){
        //返回所有二手物品信息 二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return Res.success(itemService.getItemDtoByStatus(2));
    }
    @Cacheable(value = "itemList",key = "'status_1'")
    @GetMapping("/list/manager")
    public Res<List<SecondHandItemDto>> listStatus1(){
        //返回所有二手物品信息 二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核
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
        // Step 1: 属性拷贝
        SecondHandItem item = new SecondHandItem();
        BeanUtil.copyProperties(wrapper, item);
        int userId = wrapper.getUserId();
        // 设置物品的uuid
        String uuid = UUID.randomUUID().toString();
        item.setUuid(uuid);
        // 完善数据status 时间
        int status = 1; // 1:待审核 2:发布中 3:下架
        item.setStatus(status);
        log.info("卖家发布二手物品，物品信息: {}", item);

        // Step 2: 保存二手物品
        itemService.save(item);

        // Step 3: 查询保存后的物品
        // 通过uuid查询 得到itemId
        LambdaQueryWrapper<SecondHandItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItem::getUuid, uuid);
        item = itemService.getOne(queryWrapper);
        log.info("获取物品信息: {}", item);

        // Step 4: 保存图片信息
        List<SecondHandItemImage> imageList = new ArrayList<>();
        for (String image : wrapper.getImageList()) {
            SecondHandItemImage imageItem = new SecondHandItemImage();
            imageItem.setImage(image);
            imageItem.setSecondHandItemId(item.getId());
            imageList.add(imageItem);
        }
        itemImageService.saveBatch(imageList);

        // Step 5: 批量保存标签信息
        // 3.2 创建新的标签组
        itemTagService.savaBatchByItemIdAndTagTagGroupDtoList(item.getId(),wrapper.getTagList());
//        List<SecondHandItemTag> tagList = new ArrayList<>();
//        for (TagTagGroupDto tagTagGroupDto : wrapper.getTagList()) {
//            // 通过标签id和标签组id获取标签关联对象的id
//            int tagId = tagTagGroupDto.getTagId();
//            int tagGroupId = tagTagGroupDto.getTagGroupId();
//            TagTagGroup tagTagGroup = tagTagGroupService.getTagTagGroupByTagIdAndTagGroupId(tagId, tagGroupId);
//            // 创建标签与二手物品关联对象
//            SecondHandItemTag tagItem = new SecondHandItemTag();
//            tagItem.setTagTagGroupId(tagTagGroup.getTagGroupId());
//            tagItem.setSecondHandItemId(item.getId());
//            tagList.add(tagItem);
//        }
//        itemTagService.saveBatch(tagList);

        // Step 6: 返回成功响应
        return Res.success("发布成功");
    }

    @PutMapping
    public Res<String> modifyItem(@RequestBody SecondHandWrapper wrapper){
        // 1.对象拷贝 糊涂工具类 wrapper.id->item.id
        SecondHandItem item = new SecondHandItem();
        BeanUtil.copyProperties(wrapper,item);
        log.info("卖家修改物品，参数{}",item);
        // 1.1 修改二手物品基本信息
        itemService.changeItemByItem(item);
        // 2 修改二手物品图片组
        // 2.1对原来的图片组进行删除
        LambdaQueryWrapper<SecondHandItemImage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItemImage::getSecondHandItemId,item.getId());
        itemImageService.remove(queryWrapper);
        // 2.2创建信息的图片组信息
        List<SecondHandItemImage> imageList = new ArrayList<>();
        for (String image : wrapper.getImageList()) {
            SecondHandItemImage imageItem = new SecondHandItemImage();
            imageItem.setImage(image);
            imageItem.setSecondHandItemId(item.getId());
            imageList.add(imageItem);
        }
        itemImageService.saveBatch(imageList);
        // 3. 修改物品标签组
        // 3.1 删除原有的标签组
        LambdaQueryWrapper<SecondHandItemTag> tagQueryWrapper = new LambdaQueryWrapper<>();
        tagQueryWrapper.eq(SecondHandItemTag::getSecondHandItemId,item.getId());
        itemTagService.remove(tagQueryWrapper);
        // 3.2 创建新的标签组
        itemTagService.savaBatchByItemIdAndTagTagGroupDtoList(item.getId(),wrapper.getTagList());
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
        int status = 1; // 二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核
        itemService.changeItemStatusByItemId(status,item.getId());
        return Res.success("审核中");
    }
    @PutMapping("/seller/down")
    public Res<String> sellerOffItem(@RequestBody SecondHandItem item){
        log.info("卖家下架物品，参数{}",item);
        int status = 3; // 二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核
        itemService.changeItemStatusByItemId(status,item.getId());
        return Res.success("已下架");
    }

    @CacheEvict(value = "getSomeByUserId",allEntries = true)
    @DeleteMapping
    public Res<String> sellerDeleteItem(@RequestParam int itemId){
        // 卖家删除物品 物品状态更改，数据库删除数据
        // 直接删除
        log.info("卖家删除物品，参数{}",itemId);
        // 1.删除reject信息
        LambdaQueryWrapper<SecondHandItemReject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItemReject::getItemId,itemId);
        rejectService.remove(queryWrapper);
        // 2. 删除图片组信息
        LambdaQueryWrapper<SecondHandItemImage> imageQueryWrapper = new LambdaQueryWrapper<>();
        imageQueryWrapper.eq(SecondHandItemImage::getSecondHandItemId,itemId);
        itemImageService.remove(imageQueryWrapper);
        // 3. 删除标签组信息
        LambdaQueryWrapper<SecondHandItemTag> tagQueryWrapper = new LambdaQueryWrapper<>();
        tagQueryWrapper.eq(SecondHandItemTag::getSecondHandItemId,itemId);
        itemTagService.remove(tagQueryWrapper);
        // 4. 删除物品信息
        itemService.deleteByItemId(itemId);
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

    // knife4j 接口说明
//    @ApiOperation(value = "获取所有待商品内容审核的信息",tags = "二手模块")
    @GetMapping("/list/productReview")
    public Res<List<SecondHandItemDto>> listProductReview(){
        // 获取所有待商品内容审核的信息
        // 包含获取详细信息
        log.info("获取所有待商品内容审核的信息");
        return Res.success(itemService.getItemDtoByStatus(1));
    }
    @GetMapping("/list/priceReview")
    public Res<List<SecondHandItemDto>> listPriceReview(){
        // 获取所有待商品内容审核的信息
        // 包含获取详细信息
        log.info("获取所有待商品内容审核的信息");
        return Res.success(itemService.getItemDtoByStatus(4));
    }

    // 修改商品状态 内容审核通过
    @PutMapping("/productReview/approve")
    public Res<String> itemProductReviewApproved(@RequestBody SecondHandWrapper wrapper){
        int itemId = wrapper.getItemId();
        log.info("商品内容审核通过，参数{}",itemId);
        int status = 4;
        itemService.changeStatusById(status,itemId);
        return Res.success("商品内容审核通过");
    }
    // 修改商品状态 价格审核通过
    @PutMapping("/priceReview/approve")
    public Res<String> itemPriceReviewApproved(@RequestBody SecondHandWrapper wrapper){
        int itemId = wrapper.getItemId();
        log.info("价格审核通过，参数{}",itemId);
        int status = 2;
        itemService.changeStatusById(status,itemId);
        return Res.success("商品内容审核通过");
    }

    // 商品审核不通过 body中有message
//    url: '/spz/productReview/item/reject',
    @PutMapping("/reject")
    public Res<String> itemReviewNotApproved(@RequestBody SecondHandWrapper wrapper){
        int itemId = wrapper.getItemId();
        String message = wrapper.getMessage();
        int managerId = wrapper.getManagerId();
        log.info("商品审核不通过，参数{}",wrapper);
        log.info("商品审核不通过，参数itemId:{},message:{},managerId:{}",itemId,message,managerId);
        int status = 3;
        itemService.changeStatusById(status,itemId);
        // 需要建立关联表，关联商品id和审核不通过原因
        SecondHandItemReject itemReject = new SecondHandItemReject();
        itemReject.setItemId(itemId).setMessage(message).setManagerId(managerId);
        LambdaQueryWrapper<SecondHandItemReject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItemReject::getItemId,itemId);
        rejectService.saveOrUpdate(itemReject, queryWrapper);
        return Res.success("商品审核不通过");
    }
    // getDto
    @GetMapping("/dto")
    public Res<SecondHandItemDto> getDto(@RequestParam int itemId){
        log.info("获取物品详情，参数{}", itemId);
        // 1.根据id查询item
        SecondHandItem item = itemService.getById(itemId);
        // 2.根据itemId查询itemImage
        LambdaQueryWrapper<SecondHandItemImage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHandItemImage::getSecondHandItemId,item.getId());
        List<String> imageList = itemImageService.list(queryWrapper).stream().map(SecondHandItemImage::getImage).toList();
        // 3.获取sellerUsername和sellerImage
        User seller = userService.getById(item.getUserId());
        // 4.获取tagList
        LambdaQueryWrapper<SecondHandItemTag> tagQueryWrapper = new LambdaQueryWrapper<>();
        tagQueryWrapper.eq(SecondHandItemTag::getSecondHandItemId,itemId);
        List<Integer> tagIdList = itemTagService.list(tagQueryWrapper).stream().map(SecondHandItemTag::getTagTagGroupId).toList();
        List<TagTagGroupDto> tagList = tagTagGroupService.getTagTagGroupDtoListByTagTagGroupIdList(tagIdList);
        // 5.封装itemDto
        SecondHandItemDto itemDto = new SecondHandItemDto();
        BeanUtil.copyProperties(item,itemDto);
        itemDto.setImageList(imageList);
        itemDto.setSellerUsername(seller.getUsername());
        itemDto.setSellerImage(seller.getImage());
        itemDto.setTagList(tagList);

        return Res.success(itemDto);
    }

    // 根据浏览量进行排序
    @GetMapping("/list/browse")
    public Res<List<SecondHandItemDto>> listStatus2OrderByBrowse() {
        log.info("获取所有二手物品信息");
        //返回所有二手物品信息 二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核
        return Res.success(itemService.getItemDtoByStatusAndOrderByBrowseCount(2));
    }
}

