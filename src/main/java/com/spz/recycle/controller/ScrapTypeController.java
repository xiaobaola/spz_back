package com.spz.recycle.controller;

import com.spz.public_resource.common.Res;
import com.spz.recycle.entity.ScrapType;
import com.spz.recycle.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/spz/scrapType")
@RestController
@Slf4j
public class ScrapTypeController {
    @Autowired
    private ScrapTypeService scrapTypeService;

    /**
     * Author last
     * Param
     * Return @return {@link Res }<{@link ArrayList }<{@link ScrapType }>>
     * Describe 列表2
     */
    @GetMapping("/list")
    public Res<ArrayList<ScrapType>> list2() {
        log.info("get 废品类型列表");
        ArrayList<ScrapType> scrapTypeArrayList = scrapTypeService.list2();
        return Res.success(scrapTypeArrayList);
    }

    /**
     * Param @param scrapType
     * Return @return {@link Res }<{@link String }>
     * Author last
     * Describe 根据id修改废品类型
     */
    @PutMapping
    public Res<String> updateById(@RequestBody ScrapType scrapType) {
        log.info("修改: {}", scrapType);
        scrapTypeService.updateById(scrapType);
        return Res.success("修改废品类型成功");
    }

    /**
     * Param @param scrapType
     * Return @return {@link Res }<{@link String }>
     * Author last
     * Describe 新增废品分类
     */
    @PostMapping
    public Res<String> createScrapType(@RequestBody ScrapType scrapType) {
        log.info("新增: {}", scrapType);
        scrapTypeService.insert2(scrapType);
        return Res.success("新增废品类型成功");
    }


    /**
     * Author last
     * Param @param ids 身份证
     * Return @return {@link Res }<{@link String }>
     * Describe 按 ID 删除
     */
    @DeleteMapping
    public Res<String> deleteById(@RequestParam Integer ids) {
        log.info("删除: id:{}",ids);
        scrapTypeService.deleteById(ids);
        return Res.success("删除废品类型成功");
    }

}
