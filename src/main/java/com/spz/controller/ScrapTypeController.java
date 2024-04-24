package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.scrap.ScrapType;
import com.spz.service.ScrapTypeService;
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
     * @param
     * @return @return {@link Res }<{@link ArrayList }<{@link ScrapType }>>
     * @author last
     * @describe
     */
    @GetMapping("/list")
    public Res<ArrayList<ScrapType>> list2() {
        log.info("get 废品类型列表");
        ArrayList<ScrapType> scrapTypeArrayList = scrapTypeService.list2();
        return Res.success(scrapTypeArrayList);
    }

    /**
     * @param @param scrapType
     * @return @return {@link Res }<{@link String }>
     * @author last
     * @describe 根据id修改废品类型
     */
    @PutMapping
    public Res<String> updateById(@RequestBody ScrapType scrapType) {
        log.info("修改: {}", scrapType);
        scrapTypeService.updateById(scrapType);
        return Res.success("修改废品类型成功");
    }

    /**
     * @param @param scrapType
     * @return @return {@link Res }<{@link String }>
     * @author last
     * @describe 新增废品分类
     */
    @PostMapping
    public Res<String> createScrapType(@RequestBody ScrapType scrapType) {
        log.info("新增: {}", scrapType);
        scrapTypeService.insert2(scrapType);
        return Res.success("新增废品类型成功");
    }

    @DeleteMapping
    public Res<String> deleteById(@RequestParam Integer ids) {
        log.info("删除: id:{}",ids);
        scrapTypeService.deleteById(ids);
        return Res.success("删除废品类型成功");
    }

}
