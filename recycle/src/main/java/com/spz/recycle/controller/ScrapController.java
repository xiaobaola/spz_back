package com.spz.recycle.controller;

import com.spz.common.Res;
import com.spz.entity.page.PageBean;
import com.spz.recycle.entity.Scrap;
import com.spz.recycle.service.ScrapService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spz/scrap")
@Slf4j
@Tag(name = "回收品模块")
public class ScrapController {

    private ScrapService scrapService;

    @Autowired
    public void setScrapService(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    /**
     * Param @param id
     * Return @return {@link Res }<{@link ArrayList }<{@link Scrap }>>
     * Author last
     * Describe
     */
    @GetMapping("/{id}/list")
    public Res<ArrayList<Scrap>> list(@PathVariable Integer id) {
        // 获取该类型下的所有废品 需要做缓存
        log.info("查询: id:{}", id);
        ArrayList<Scrap> list = scrapService.getListByTypeId(id);
        return Res.success(list);
    }



    /**
     * Param @param   page
     * Param pageSize
     * Param name
     * Param begin
     * Param end
     * Return @return {@link Res }<{@link PageBean }>
     * Author last
     * Describe 分页查询
     */
    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                 String name,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{}", page, pageSize, name, begin, end);
        PageBean pageBean = scrapService.page(page, pageSize, name, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<Scrap> getById(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        Scrap scrap = scrapService.getById(id);
        return Res.success(scrap);
    }

    @PutMapping
    public Res<String> updateById(@RequestBody Scrap scrap) {
        log.info("更新: 回收品{}", scrap);
        // 需要删除缓存
        scrapService.changeById(scrap);
        return Res.success("修改回收品成功");
    }

    @DeleteMapping
    public Res<String> deleteByIds(@RequestParam List<Integer> ids) {
        log.info("删除: 回收品id:{}", ids);
        scrapService.deleteByIds(ids);
        return Res.success("删除回收品成功");
    }

    @PostMapping
    public Res<String> insert(@RequestBody Scrap scrap) {
        log.info("插入: {}", scrap);
        scrapService.add(scrap);
        return Res.success("新增回收品成功");
    }
}
